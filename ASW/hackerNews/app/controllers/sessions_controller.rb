class SessionsController < ApplicationController
  
  def destroy
    session.delete(:user_id)
    @current_user = nil
    redirect_to root_url
  end
end