defmodule Calculator do

  def calculate_product_tax(price, region) do
    base_rate = 0.1

    if region == :europe do
      price * (base_rate + 0.05) # 15% for Europe
    else
      if price > 1000 do
        price * base_rate * 0.9 # 10% tax with 10% discount for high value
      else
        price * base_rate # Standard 10% tax
      end
    end
  end

  def calculate_service_tax(price, region) do
    base_rate = 0.1

    if region == :europe do
      price * (base_rate + 0.05) # 15% for Europe
    else
      if price > 1000 do
        price * base_rate * 0.9 # 10% tax with 10% discount
      else
        price * base_rate # Standard 10% tax
      end
    end
  end

  def calculate_shipping_tax(price, region) do
    base_rate = 0.1

    if region == :europe do
      price * (base_rate + 0.05) # 15% for Europe
    else
      if price > 1000 do
        price * base_rate * 0.9 # 10% tax with 10% discount
      else
        price * base_rate # Standard 10% tax
      end
    end
  end
end
