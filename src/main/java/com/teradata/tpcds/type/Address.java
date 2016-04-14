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

import com.teradata.tpcds.Column;
import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Table;
import com.teradata.tpcds.distribution.CitiesDistribution;
import com.teradata.tpcds.distribution.FipsCountyDistribution;
import com.teradata.tpcds.distribution.StreetNamesDistribution;
import com.teradata.tpcds.distribution.StreetTypeDistribution;
import com.teradata.tpcds.random.RandomNumberStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.teradata.tpcds.Table.ACTIVE_CITIES;
import static com.teradata.tpcds.Table.ACTIVE_COUNTIES;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static java.lang.String.format;

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
    private final int plus4;
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
                   int plus4,
                   int gmtOffset)
    {
        checkNotNull(suiteNumber, "suiteNumber is null");
        checkArgument(streetNumber >= 1 && streetNumber <= 1000, "streetNumber is not between 1 and 1000");
        checkNotNull(streetName1, "streetName1 is null");
        checkNotNull(streetName2, "streetName2 is null");
        checkNotNull(streetType, "streetType is null");
        checkNotNull(city, "city is null");
        checkNotNull(country, "country is null");
        checkArgument(zip >= 0 && zip <= 99999, "zip is not between 0 and 99999");
        checkArgument(plus4 >= 0 && plus4 <= 9999, "plus4 is not between 0 and 9999");
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
        this.plus4 = plus4;
        this.gmtOffset = gmtOffset;
    }

    public static Address makeAddressForColumn(Column column, Scaling scaling)
    {
        AddressBuilder builder = new AddressBuilder();
        RandomNumberStream randomNumberStream = column.getRandomNumberStream();
        builder.setStreetNumber(generateUniformRandomInt(1, 1000, randomNumberStream));
        builder.setStreetName1(StreetNamesDistribution.pickRandomValue(1, 1, randomNumberStream));
        builder.setStreetName2(StreetNamesDistribution.pickRandomValue(1, 2, randomNumberStream));
        builder.setStreetType(StreetTypeDistribution.pickRandomValue(1, 1, randomNumberStream));

        int randomInt = generateUniformRandomInt(0, 100, randomNumberStream);
        if (randomInt % 2 == 1) {  // if i is odd, suiteNumber is a number
            builder.setSuiteNumber(format("Suite %d", (randomInt / 2) * 10));
        }
        else { // if i is even, suiteNumber is a letter
            builder.setSuiteNumber(format("Suite %c", (randomInt / 2) % 25) + 'A');
        }

        Table table = column.getTable();
        int rowCount = (int) scaling.getRowCount(column.getTable());
        String city;
        if ((table.isSmall())) {
            int maxCities = (int) scaling.getRowCount(ACTIVE_CITIES);
            randomInt = generateUniformRandomInt(1, (maxCities > rowCount) ? rowCount : maxCities, randomNumberStream);
            city = CitiesDistribution.getMemberString(randomInt, 1);
        }
        else {
            city = CitiesDistribution.pickRandomValue(1, 6, column.getRandomNumberStream());
        }
        builder.setCity(city);

        // county is picked from a distribution, based on population and keys the rest
        int regionNumber;
        if (table.isSmall()) {
            int maxCounties = (int) scaling.getRowCount(ACTIVE_COUNTIES);
            regionNumber = generateUniformRandomInt(1, (maxCounties > rowCount) ? rowCount : maxCounties, randomNumberStream);
            builder.setCounty(FipsCountyDistribution.getMemberString(regionNumber, 2));
        }
        else {
            builder.setCounty(FipsCountyDistribution.pickRandomValue(2, 1, randomNumberStream));
            regionNumber = 0;  //TODO: this is supposed to be equal to index + 1 of the random value chosen above.  In the c code both happen in pick_distribution, which is an alias for op 0 of dist_op.
        }

        // match state with the selected region/county
        builder.setState(FipsCountyDistribution.getMemberString(regionNumber, 3));

        // match the zip prefix with the selected region/county
        int zip = computeCityHash(city);

        // 00000 - 00600 are unused. Avoid them
        char zipPrefixChar = FipsCountyDistribution.getMemberString(regionNumber, 5).charAt(0);
        if (zipPrefixChar == '0' && zip < 9400) {
            zip += 600;
        }
        builder.setZip(zip + (zipPrefixChar - '0') * 10000);

        String addr = format("%d %s %s %s", builder.getStreetNumber(), builder.getStreetName1(), builder.getStreetName2(), builder.getStreetType());
        builder.setPlus4(computeCityHash(addr));
        builder.setGmtOffset(FipsCountyDistribution.getMemberInt(regionNumber, 6));
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
        private int plus4;
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

        public AddressBuilder setPlus4(int plus4)
        {
            this.plus4 = plus4;
            return this;
        }

        public AddressBuilder setGmtOffset(int gmtOffset)
        {
            this.gmtOffset = gmtOffset;
            return this;
        }

        public Address build()
        {
            return new Address(suiteNumber, streetNumber, streetName1, streetName2, streetType, city, county, state, country, zip, plus4, gmtOffset);
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
