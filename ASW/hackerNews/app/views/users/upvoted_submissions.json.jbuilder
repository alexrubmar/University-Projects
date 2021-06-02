json.array!(@posts) do |post|
  json.extract! post, :id, :title, :url, :text, :created_at, :updated_at, :user_id, :cached_votes_total
end