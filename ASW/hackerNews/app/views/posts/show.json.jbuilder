json.post do
  json.id @post.id
  json.title @post.title
  json.url @post.url
  json.text @post.text
  json.user_id @post.user_id
  json.created_at @post.created_at
  json.updated_at @post.updated_at
  json.votes @post.cached_votes_total
end
json.comments @post.comments, :id, :body, :user_id, :created_at, :updated_at, :cached_votes_total

