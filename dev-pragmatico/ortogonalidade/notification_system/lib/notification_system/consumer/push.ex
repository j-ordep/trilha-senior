defmodule NotificationSystem.Consumer.Push do
  alias NotificationSystem.Notification

  def handle(%Notification{type: :push, message: msg, metadata: meta}) do
    IO.puts("Sending Push: #{msg} to device #{meta[:device_id]}")
  end

end
