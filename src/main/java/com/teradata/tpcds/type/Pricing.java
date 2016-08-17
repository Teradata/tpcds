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

import com.google.common.collect.ImmutableMap;
import com.teradata.tpcds.TpcdsException;
import com.teradata.tpcds.column.Column;
import com.teradata.tpcds.random.RandomNumberStream;

import java.util.Map;

import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_DISCOUNT_MAX;
import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_MARKUP_MAX;
import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_QUANTITY_MAX;
import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_WHOLESALE_MAX;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_PRICING;
import static com.teradata.tpcds.column.WebSalesColumn.WS_PRICING;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomDecimal;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Decimal.NINE_PERCENT;
import static com.teradata.tpcds.type.Decimal.ONE;
import static com.teradata.tpcds.type.Decimal.ONE_HALF;
import static com.teradata.tpcds.type.Decimal.ONE_HUNDRED;
import static com.teradata.tpcds.type.Decimal.ZERO;
import static com.teradata.tpcds.type.Decimal.add;
import static com.teradata.tpcds.type.Decimal.divide;
import static com.teradata.tpcds.type.Decimal.multiply;
import static com.teradata.tpcds.type.Decimal.negate;
import static com.teradata.tpcds.type.Decimal.subtract;

public class Pricing
{
    public static final int QUANTITY_MIN = 1;
    public static final Decimal MARKUP_MIN = new Decimal(0, 2);
    public static final Decimal DISCOUNT_MIN = new Decimal(0, 2);

    // TODO: add Limits for other columns as they are relevant
    private static final Map<Column, Limits> LIMITS_PER_COLUMN = ImmutableMap.of(
            CS_PRICING, new Limits(CS_QUANTITY_MAX, CS_MARKUP_MAX, CS_DISCOUNT_MAX, CS_WHOLESALE_MAX),
            SS_PRICING, new Limits(100, ONE, ONE, ONE_HUNDRED),
            WS_PRICING, new Limits(100, new Decimal(200, 2), ONE, ONE_HUNDRED));

    private final Decimal wholesaleCost;
    private final Decimal listPrice;
    private final Decimal salesPrice;
    private final int quantity;
    private final Decimal extDiscountAmount;
    private final Decimal extSalesPrice;
    private final Decimal extWholesaleCost;
    private final Decimal extListPrice;
    private final Decimal taxPercent;
    private final Decimal extTax;
    private final Decimal couponAmount;
    private final Decimal shipCost;
    private final Decimal extShipCost;
    private final Decimal netPaid;
    private final Decimal netPaidIncludingTax;
    private final Decimal netPaidIncludingShipping;
    private final Decimal netPaidIncludingShippingAndTax;
    private final Decimal netProfit;
    private final Decimal refundedCash;
    private final Decimal reversedCharge;
    private final Decimal storeCredit;
    private final Decimal fee;
    private final Decimal netLoss;

    private Pricing(Decimal wholesaleCost,
            Decimal listPrice,
            Decimal salesPrice,
            int quantity,
            Decimal extDiscountAmount,
            Decimal extSalesPrice,
            Decimal extWholesaleCost,
            Decimal extListPrice,
            Decimal taxPercent,
            Decimal extTax,
            Decimal couponAmount,
            Decimal shipCost,
            Decimal extShipCost,
            Decimal netPaid,
            Decimal netPaidIncludingTax,
            Decimal netPaidIncludingShipping,
            Decimal netPaidIncludingShippingAndTax,
            Decimal netProfit,
            Decimal refundedCash,
            Decimal reversedCharge,
            Decimal storeCredit,
            Decimal fee,
            Decimal netLoss)
    {
        this.wholesaleCost = wholesaleCost;
        this.listPrice = listPrice;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.extDiscountAmount = extDiscountAmount;
        this.extSalesPrice = extSalesPrice;
        this.extWholesaleCost = extWholesaleCost;
        this.extListPrice = extListPrice;
        this.taxPercent = taxPercent;
        this.extTax = extTax;
        this.couponAmount = couponAmount;
        this.shipCost = shipCost;
        this.extShipCost = extShipCost;
        this.netPaid = netPaid;
        this.netPaidIncludingTax = netPaidIncludingTax;
        this.netPaidIncludingShipping = netPaidIncludingShipping;
        this.netPaidIncludingShippingAndTax = netPaidIncludingShippingAndTax;
        this.netProfit = netProfit;
        this.refundedCash = refundedCash;
        this.reversedCharge = reversedCharge;
        this.storeCredit = storeCredit;
        this.fee = fee;
        this.netLoss = netLoss;
    }

    public static Pricing generatePricingForSalesTable(Column column)
    {
        if (!LIMITS_PER_COLUMN.containsKey(column)) {
            throw new TpcdsException("No price limits for column: " + column);
        }

        Limits limits = LIMITS_PER_COLUMN.get(column);

        RandomNumberStream randomNumberStream = column.getRandomNumberStream();
        int quantity = generateUniformRandomInt(QUANTITY_MIN, limits.getMaxQuantitySold(), randomNumberStream);
        Decimal decimalQuantity = Decimal.fromInteger(quantity);
        Decimal wholesaleCost = generateUniformRandomDecimal(new Decimal(100, 2), limits.getMaxWholesaleCost(), randomNumberStream);
        Decimal extWholesaleCost = multiply(decimalQuantity, wholesaleCost);

        Decimal markup = generateUniformRandomDecimal(MARKUP_MIN, limits.getMaxMarkup(), randomNumberStream);
        markup = add(markup, ONE);
        Decimal listPrice = multiply(wholesaleCost, markup);

        Decimal discount = negate(generateUniformRandomDecimal(DISCOUNT_MIN, limits.getMaxDiscount(), randomNumberStream));
        discount = add(discount, ONE);
        Decimal salesPrice = multiply(listPrice, discount);
        Decimal extListPrice = multiply(listPrice, decimalQuantity);
        Decimal extSalesPrice = multiply(salesPrice, decimalQuantity);
        Decimal extDiscountAmount = subtract(extListPrice, extSalesPrice);

        Decimal coupon = generateUniformRandomDecimal(ZERO, ONE, randomNumberStream);
        int couponUsage = generateUniformRandomInt(1, 100, randomNumberStream);
        Decimal couponAmount;
        if (couponUsage <= 20) { // 20% of sales employ a coupon
            couponAmount = multiply(extSalesPrice, coupon);
        }
        else {
            couponAmount = ZERO;
        }

        Decimal netPaid = subtract(extSalesPrice, couponAmount);

        Decimal shipping = generateUniformRandomDecimal(ZERO, ONE_HALF, randomNumberStream);
        Decimal shipCost = multiply(listPrice, shipping);
        Decimal extShipCost = multiply(shipCost, decimalQuantity);
        Decimal netPaidIncludingShipping = add(netPaid, extShipCost);
        Decimal taxPercent = generateUniformRandomDecimal(ZERO, NINE_PERCENT, randomNumberStream);
        Decimal extTax = multiply(netPaid, taxPercent);
        Decimal netPaidIncludingTax = add(netPaid, extTax);
        Decimal netPaidIncludingShippingAndTax = add(netPaidIncludingShipping, extTax);
        Decimal netProfit = subtract(netPaid, extWholesaleCost);

        // only relevant for returns
        Decimal refundedCash = ZERO;
        Decimal reversedCharge = ZERO;
        Decimal storeCredit = ZERO;
        Decimal fee = ZERO;
        Decimal netLoss = ZERO;

        return new Pricing(wholesaleCost,
                listPrice,
                salesPrice,
                quantity,
                extDiscountAmount,
                extSalesPrice,
                extWholesaleCost,
                extListPrice,
                taxPercent,
                extTax,
                couponAmount,
                shipCost,
                extShipCost,
                netPaid,
                netPaidIncludingTax,
                netPaidIncludingShipping,
                netPaidIncludingShippingAndTax,
                netProfit,
                refundedCash,
                reversedCharge,
                storeCredit,
                fee,
                netLoss);
    }

    public static Pricing generatePricingForReturnsTable(Column column, int quantity, Pricing basePricing)
    {
        Decimal wholesaleCost = basePricing.wholesaleCost;
        Decimal listPrice = basePricing.listPrice;
        Decimal salesPrice = basePricing.salesPrice;
        Decimal taxPercent = basePricing.taxPercent;
        Decimal extDiscountAmount = basePricing.extDiscountAmount;
        Decimal couponAmount = basePricing.couponAmount;

        Decimal decimalQuantity = Decimal.fromInteger(quantity);
        Decimal extWholesaleCost = multiply(decimalQuantity, wholesaleCost);
        Decimal extListPrice = multiply(listPrice, decimalQuantity);
        Decimal extSalesPrice = multiply(salesPrice, decimalQuantity);
        Decimal netPaid = extSalesPrice;
        Decimal shipping = generateUniformRandomDecimal(ZERO, ONE_HALF, column.getRandomNumberStream());
        Decimal shipCost = multiply(listPrice, shipping);
        Decimal extShipCost = multiply(shipCost, decimalQuantity);
        Decimal netPaidIncludingShipping = add(netPaid, extShipCost);
        Decimal extTax = multiply(netPaid, taxPercent);
        Decimal netPaidIncludingTax = add(netPaid, extTax);
        Decimal netPaidIncludingShippingAndTax = add(netPaidIncludingShipping, extTax);
        Decimal netProfit = subtract(netPaid, extWholesaleCost);

        //see to it that the returned amounts add up to the total returned
        // allocate some of return to cash
        Decimal cashPercentage = Decimal.fromInteger(generateUniformRandomInt(0, 100, column.getRandomNumberStream()));
        Decimal refundedCash = multiply(divide(cashPercentage, ONE_HUNDRED), netPaid);

        // allocate some to reversed charges
        Decimal creditPercent = Decimal.fromInteger(generateUniformRandomInt(1, 100, column.getRandomNumberStream()));
        creditPercent = divide(creditPercent, ONE_HUNDRED);
        Decimal paidMinusRefunded = subtract(netPaid, refundedCash);
        Decimal reversedCharge = multiply(creditPercent, paidMinusRefunded);

        // the rest is store credit
        Decimal storeCredit = subtract(netPaid, reversedCharge);
        storeCredit = subtract(storeCredit, refundedCash);

        // pick a fee for the return
        Decimal fee = generateUniformRandomDecimal(ONE_HALF, ONE_HUNDRED, column.getRandomNumberStream());

        // and calculate the net effect
        Decimal netLoss = subtract(netPaidIncludingShippingAndTax, storeCredit);
        netLoss = subtract(netLoss, refundedCash);
        netLoss = subtract(netLoss, reversedCharge);
        netLoss = add(netLoss, fee);

        return new Pricing(wholesaleCost,
                listPrice,
                salesPrice,
                quantity,
                extDiscountAmount,
                extSalesPrice,
                extWholesaleCost,
                extListPrice,
                taxPercent,
                extTax,
                couponAmount,
                shipCost,
                extShipCost,
                netPaid,
                netPaidIncludingTax,
                netPaidIncludingShipping,
                netPaidIncludingShippingAndTax,
                netProfit,
                refundedCash,
                reversedCharge,
                storeCredit,
                fee,
                netLoss);
    }

    public Decimal getNetLoss()
    {
        return netLoss;
    }

    public Decimal getExtDiscountAmount()
    {
        return extDiscountAmount;
    }

    public Decimal getExtSalesPrice()
    {
        return extSalesPrice;
    }

    public Decimal getExtWholesaleCost()
    {
        return extWholesaleCost;
    }

    public Decimal getExtListPrice()
    {
        return extListPrice;
    }

    public Decimal getTaxPercent()
    {
        return taxPercent;
    }

    public Decimal getExtTax()
    {
        return extTax;
    }

    public Decimal getCouponAmount()
    {
        return couponAmount;
    }

    public Decimal getShipCost()
    {
        return shipCost;
    }

    public Decimal getExtShipCost()
    {
        return extShipCost;
    }

    public Decimal getNetPaid()
    {
        return netPaid;
    }

    public Decimal getNetPaidIncludingTax()
    {
        return netPaidIncludingTax;
    }

    public Decimal getNetPaidIncludingShipping()
    {
        return netPaidIncludingShipping;
    }

    public Decimal getNetPaidIncludingShippingAndTax()
    {
        return netPaidIncludingShippingAndTax;
    }

    public Decimal getNetProfit()
    {
        return netProfit;
    }

    public Decimal getRefundedCash()
    {
        return refundedCash;
    }

    public Decimal getReversedCharge()
    {
        return reversedCharge;
    }

    public Decimal getStoreCredit()
    {
        return storeCredit;
    }

    public Decimal getFee()
    {
        return fee;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public Decimal getWholesaleCost()
    {
        return wholesaleCost;
    }

    public Decimal getListPrice()
    {
        return listPrice;
    }

    public Decimal getSalesPrice()
    {
        return salesPrice;
    }

    private static class Limits
    {
        private final int maxQuantitySold;
        private final Decimal maxMarkup;
        private final Decimal maxDiscount;
        private final Decimal maxWholesaleCost;

        Limits(int maxQuantitySold, Decimal maxMarkup, Decimal maxDiscount, Decimal maxWholesaleCost)
        {
            this.maxQuantitySold = maxQuantitySold;
            this.maxMarkup = maxMarkup;
            this.maxDiscount = maxDiscount;
            this.maxWholesaleCost = maxWholesaleCost;
        }

        Decimal getMaxDiscount()
        {
            return maxDiscount;
        }

        int getMaxQuantitySold()
        {
            return maxQuantitySold;
        }

        Decimal getMaxMarkup()
        {
            return maxMarkup;
        }

        Decimal getMaxWholesaleCost()
        {
            return maxWholesaleCost;
        }
    }
}
