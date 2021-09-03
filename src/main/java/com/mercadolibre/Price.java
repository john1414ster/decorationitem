package com.mercadolibre;

import java.util.Date;
import java.util.List;

public class Price{
    public String id;
    public String type;
    public Conditions conditions;
    public int amount;
    public Object regular_amount;
    public String currency_id;
    public String exchange_rate_context;
    public Metadata metadata;
    public Date last_updated;
    public List<Price> prices;
    public Presentation presentation;
    public List<Object> payment_method_prices;
    public List<Object> reference_prices;
    public List<Object> purchase_discounts;
    public List<Object> differing_price_variations;
}