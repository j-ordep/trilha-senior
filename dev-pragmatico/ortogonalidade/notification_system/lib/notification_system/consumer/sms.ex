defmodule NotificationSystem.Consumer.SMS do
  alias NotificationSystem.Notification

  def handle(%Notification{type: :sms, message: msg, metadata: meta}) do
    IO.puts("Sending SMS: #{msg} to device #{meta[:phone]}")
  end

end
