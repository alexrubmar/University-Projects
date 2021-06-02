json.extract! user, :id, :name, :about, :email, :password, :karma, :created_at, :updated_at
json.url user_url(user, format: :json)
