json.array!(@posts) do |post|
  if post.url != ""
    json.extract! post, :id, :title, :url, :text, :created_at, :updated_at, :user_id, :cached_votes_total
  end
end