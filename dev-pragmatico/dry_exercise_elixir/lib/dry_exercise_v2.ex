defmodule TaxCalculatorV2 do
  @europe_extra 0.05
  @high_price_discount 0.9

  def calculate(price, type, region) do
    price
    |> apply_base_rate(type)
    |> apply_region_modifier(region)
    |> apply_high_price_discount(price)
    |> Float.round(2)
  end

  # -------------------------
  # Base rate por tipo
  # -------------------------

  defp apply_base_rate(price, :product) do
    price * 0.12
  end

  defp apply_base_rate(price, :service) do
    price * 0.18
  end

  defp apply_base_rate(price, :shipping) do
    price * 0.05
  end

  # -------------------------
  # Ajuste por região
  # -------------------------

  defp apply_region_modifier(tax, :europe) do
    tax * (1 + @europe_extra)
  end

  defp apply_region_modifier(tax, _region) do
    tax
  end

  # -------------------------
  # Desconto para preço alto
  # -------------------------

  defp apply_high_price_discount(tax, price) when price > 1000 do
    tax * @high_price_discount
  end

  defp apply_high_price_discount(tax, _price) do
    tax
  end
end
