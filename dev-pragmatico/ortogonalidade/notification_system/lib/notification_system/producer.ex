defmodule NotificationSystem.Producer do
  alias NotificationSystem.Notification
  alias NotificationSystem.Consumer.{Email, Push, SMS}

  @consumer %{
    email: Email,
    push: Push,
    sms: SMS,
  }

  def publish(%Notification{type: type} = notification) do
    case Map.get(@consumer, type) do
      nil ->
        {:error, :unknown_type}
        
      consumer ->
        consumer.handle(notification)
        :ok
    end
  end
end

  # @consumer [Email, Push, SMS

  # def publish(%Notification{type: type} = notification) do
    # Enum.each(@consumer, fn consumer ->
    #   consumer.handle(notification)
    # end)
  # end
