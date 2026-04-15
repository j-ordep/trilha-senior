defmodule NotificationSystem.Consumer.Email do
  alias NotificationSystem.Notification

  def handle(%Notification{type: :email, message: msg, metadata: meta}) do
    IO.puts("Sending Email: #{msg} to device #{meta[:to]}")
  end

end
