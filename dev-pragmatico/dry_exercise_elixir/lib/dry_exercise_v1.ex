defmodule TaxCalculatorV1 do

  # O Elixir escolhe qual função executar baseado no padrão dos argumentos(parametro)

  # constante
  @base_rate 0.1

  def calculate(price, region), do: calculate_common_tax(price, region)

  defp calculate_common_tax(price, :europe) do
    price * (@base_rate + 0.05) |> Float.round(2)
  end

  defp calculate_common_tax(price, _region) when price > 1000 do # Guard Clause (é como se fosse um if para rodar a função)
    price * @base_rate * 0.9 |> Float.round(2)
  end

  defp calculate_common_tax(price, _region) do
    price * @base_rate |> Float.round(2)
  end
end
