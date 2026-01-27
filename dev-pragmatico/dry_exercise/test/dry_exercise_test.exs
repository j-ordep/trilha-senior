defmodule DryExerciseTest do
  use ExUnit.Case
  doctest DryExercise

  test "greets the world" do
    assert DryExercise.hello() == :world
  end
end
