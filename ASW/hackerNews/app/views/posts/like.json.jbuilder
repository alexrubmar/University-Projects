json.post do
  json.votes @post.cached_votes_total
end