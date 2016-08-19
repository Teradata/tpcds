/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teradata.tpcds.type;

import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Table;
import com.teradata.tpcds.column.generator.GeneratorColumn;
import com.teradata.tpcds.distribution.FipsCountyDistribution;
import com.teradata.tpcds.random.RandomNumberStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.PseudoTableScalingInfos.ACTIVE_CITIES;
import static com.teradata.tpcds.PseudoTableScalingInfos.ACTIVE_COUNTIES;
import static com.teradata.tpcds.distribution.AddressDistributions.CitiesWeights.UNIFIED_STEP_FUNCTION;
import static com.teradata.tpcds.distribution.AddressDistributions.StreetNamesWeights.DEFAULT;
import static com.teradata.tpcds.distribution.AddressDistributions.StreetNamesWeights.HALF_EMPTY;
import static com.teradata.tpcds.distribution.AddressDistributions.getCityAtIndex;
import static com.teradata.tpcds.distribution.AddressDistributions.pickRandomCity;
import static com.teradata.tpcds.distribution.AddressDistributions.pickRandomStreetName;
import static com.teradata.tpcds.distribution.AddressDistributions.pickRandomStreetType;
import static com.teradata.tpcds.distribution.FipsCountyDistribution.FipsWeights.UNIFORM;
import static com.teradata.tpcds.distribution.FipsCountyDistribution.getCountyAtIndex;
import static com.teradata.tpcds.distribution.FipsCountyDistribution.getGmtOffsetAtIndex;
import static com.teradata.tpcds.distribution.FipsCountyDistribution.getStateAbbreviationAtIndex;
import static com.teradata.tpcds.distribution.FipsCountyDistribution.getZipPrefixAtIndex;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class Address
{
    private final String suiteNumber;
    private final int streetNumber;
    private final String streetName1;
    private final String streetName2;
    private final String streetType;
    private final String city;
    private final String county;
    private final String state;
    private final String country;
    private final int zip;
    private final int gmtOffset;

    public Address(String suiteNumber,
            int streetNumber,
            String streetName1,
            String streetName2,
            String streetType,
            String city,
            String county,
            String state,
            String country,
            int zip,
            int gmtOffset)
    {
        requireNonNull(suiteNumber, "suiteNumber is null");
        checkArgument(streetNumber >= 1 && streetNumber <= 1000, "streetNumber is not between 1 and 1000");
        requireNonNull(streetName1, "streetName1 is null");
        requireNonNull(streetName2, "streetName2 is null");
        requireNonNull(streetType, "streetType is null");
        requireNonNull(city, "city is null");
        requireNonNull(country, "country is null");
        checkArgument(zip >= 0 && zip <= 99999, "zip is not between 0 and 99999");
        this.suiteNumber = suiteNumber;
        this.streetNumber = streetNumber;
        this.streetName1 = streetName1;
        this.streetName2 = streetName2;
        this.streetType = streetType;
        this.city = city;
        this.county = county;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.gmtOffset = gmtOffset;
    }

    public static Address makeAddressForColumn(GeneratorColumn column, Scaling scaling)
    {
        AddressBuilder builder = new AddressBuilder();
        RandomNumberStream randomNumberStream = column.getRandomNumberStream();
        builder.setStreetNumber(generateUniformRandomInt(1, 1000, randomNumberStream));
        builder.setStreetName1(pickRandomStreetName(DEFAULT, randomNumberStream));
        builder.setStreetName2(pickRandomStreetName(HALF_EMPTY, randomNumberStream));
        builder.setStreetType(pickRandomStreetType(randomNumberStream));

        int randomInt = generateUniformRandomInt(1, 100, randomNumberStream);
        if (randomInt % 2 == 1) {  // if i is odd, suiteNumber is a number
            builder.setSuiteNumber(format("Suite %d", (randomInt / 2) * 10));
        }
        else { // if i is even, suiteNumber is a letter
            builder.setSuiteNumber(format("Suite %c", ((randomInt / 2) % 25) + 'A'));
        }

        Table table = column.getTable();
        int rowCount = (int) scaling.getRowCount(column.getTable());
        String city;
        if ((table.isSmall())) {
            int maxCities = (int) ACTIVE_CITIES.getRowCountForScale(scaling.getScale());
            randomInt = generateUniformRandomInt(0, (maxCities > rowCount) ? rowCount - 1 : maxCities - 1, randomNumberStream);
            city = getCityAtIndex(randomInt);
        }
        else {
            city = pickRandomCity(UNIFIED_STEP_FUNCTION, column.getRandomNumberStream());
        }
        builder.setCity(city);

        // county is picked from a distribution, based on population and keys the rest
        int regionNumber;
        if (table.isSmall()) {
            int maxCounties = (int) ACTIVE_COUNTIES.getRowCountForScale(scaling.getScale());
            regionNumber = generateUniformRandomInt(0, (maxCounties > rowCount) ? rowCount - 1 : maxCounties - 1, randomNumberStream);
            builder.setCounty(getCountyAtIndex(regionNumber));
        }
        else {
            regionNumber = FipsCountyDistribution.pickRandomIndex(UNIFORM, randomNumberStream);
            String county = getCountyAtIndex(regionNumber);
            builder.setCounty(county);
        }

        // match state with the selected region/county
        builder.setState(getStateAbbreviationAtIndex(regionNumber));

        // match the zip prefix with the selected region/county
        int zip = computeCityHash(city);

        // 00000 - 00600 are unused. Avoid them
        int zipPrefix = getZipPrefixAtIndex(regionNumber);
        if (zipPrefix == 0 && zip < 9400) {
            zip += 600;
        }
        builder.setZip(zip + (zipPrefix) * 10000);

        builder.setGmtOffset(getGmtOffsetAtIndex(regionNumber));
        builder.setCountry("United States");

        return builder.build();
    }

    private static int computeCityHash(String name)
    {
        int hashValue = 0;
        int result = 0;
        for (int i = 0; i < name.length(); i++) {
            hashValue *= 26;
            hashValue += name.charAt(i) - 'A';
            if (hashValue > 1000000) {
                hashValue %= 10000;
                result += hashValue;
                hashValue = 0;
            }
        }

        hashValue %= 1000;
        result += hashValue;
        result %= 10000;   // looking for a 4 digit result
        return result;
    }

    public int getStreetNumber()
    {
        return streetNumber;
    }

    public String getStreetName()
    {
        return format("%s %s", streetName1, streetName2);
    }

    public String getSuiteNumber()
    {
        return suiteNumber;
    }

    public String getStreetType()
    {
        return streetType;
    }

    public String getCity()
    {
        return city;
    }

    public String getCounty()
    {
        return county;
    }

    public String getState()
    {
        return state;
    }

    public int getZip()
    {
        return zip;
    }

    public String getCountry()
    {
        return country;
    }

    public int getGmtOffset()
    {
        return gmtOffset;
    }

    public String getStreetName1()
    {
        return streetName1;
    }

    public String getStreetName2()
    {
        return streetName2;
    }

    public static class AddressBuilder
    {
        private String suiteNumber;
        private int streetNumber;
        private String streetName1;
        private String streetName2;
        private String streetType;
        private String city;
        private String county;
        private String state;
        private String country;
        private int zip;
        private int gmtOffset;

        public AddressBuilder setSuiteNumber(String suiteNumber)
        {
            this.suiteNumber = suiteNumber;
            return this;
        }

        public AddressBuilder setStreetNumber(int streetNumber)
        {
            this.streetNumber = streetNumber;
            return this;
        }

        public AddressBuilder setStreetName1(String streetName1)
        {
            this.streetName1 = streetName1;
            return this;
        }

        public AddressBuilder setStreetName2(String streetName2)
        {
            this.streetName2 = streetName2;
            return this;
        }

        public AddressBuilder setStreetType(String streetType)
        {
            this.streetType = streetType;
            return this;
        }

        public AddressBuilder setCity(String city)
        {
            this.city = city;
            return this;
        }

        public AddressBuilder setCounty(String county)
        {
            this.county = county;
            return this;
        }

        public AddressBuilder setState(String state)
        {
            this.state = state;
            return this;
        }

        public AddressBuilder setCountry(String country)
        {
            this.country = country;
            return this;
        }

        public AddressBuilder setZip(int zip)
        {
            this.zip = zip;
            return this;
        }

        public AddressBuilder setGmtOffset(int gmtOffset)
        {
            this.gmtOffset = gmtOffset;
            return this;
        }

        public Address build()
        {
            return new Address(suiteNumber, streetNumber, streetName1, streetName2, streetType, city, county, state, country, zip, gmtOffset);
        }

        public int getStreetNumber()
        {
            return streetNumber;
        }

        public String getStreetName1()
        {
            return streetName1;
        }

        public String getStreetName2()
        {
            return streetName2;
        }

        public String getStreetType()
        {
            return streetType;
        }
    }
}
