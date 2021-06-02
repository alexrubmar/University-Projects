require "test_helper"

class PostsNewControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get posts_new_index_url
    assert_response :success
  end
end
