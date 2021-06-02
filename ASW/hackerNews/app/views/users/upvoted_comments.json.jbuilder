json.array!(@comments) do |comment|
  json.extract! comment, :id, :body, :post_id, :parent_id, :user_id, :created_at, :updated_at, :cached_votes_total
end