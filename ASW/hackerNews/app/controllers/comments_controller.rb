class CommentsController < ApplicationController
  before_action :set_comment, only: [:show, :edit, :update, :destroy, :like, :unlike]


  # GET /comments
  # GET /comments.json
  def index
    @comments = Comment.all
  end

  # GET /comments/1
  # GET /comments/1.json
  def show
  end

  # GET /comments/new
  def new
    @post = Post.find(params[:post_id])
    @comment = @post.comments.new(parent_id: params[:parent_id])
  end

  # GET /comments/1/edit
  def edit
  end

  # POST /comments
  # POST /comments.json
  def create
    body = params[:body];
    @post = Post.find(params[:post_id])
    
    usr_id = 0;
    if request.headers['X-API-KEY'].nil?
      if params[:api_key].nil?
        usr_id = session[:user_id]
      else
        usr_id = params[:api_key]
      end
    else
      usr_id = request.headers['X-API-KEY']
    end
    @user = User.find_by(id: usr_id)
    if @user.nil?
      redirect_to "/users/new"
    else
      if (!body.blank?)
        @comment = @user.comments.new(comment_params)
        @comment = @post.comments.new(comment_params)
        @comment.user = User.find_by(id: usr_id)
        
        respond_to do |format|
          if @comment.save
            format.html { redirect_to @post.becomes(Post), notice: 'Comment was successfully created.' }
            format.json { render :show, status: :created, location: @comment }
          else
            format.html { render :new }
            format.json { render json: @comment.errors, status: :unprocessable_entity }
          end
        end
      end
    end
  end

  # PATCH/PUT /comments/1
  # PATCH/PUT /comments/1.json
  def update
    respond_to do |format|
      if @comment.update(comment_params)
        format.html { redirect_to @comment, notice: 'Comment was successfully updated.' }
        format.json { render :show, status: :ok, location: @comment }
      else
        format.html { render :edit }
        format.json { render json: @comment.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /comments/1
  # DELETE /comments/1.json
  def destroy
    @comment.destroy
    respond_to do |format|
      format.html { redirect_to comments_url, notice: 'Comment was successfully destroyed.' }
      format.json { head :no_content }
    end
  end
  
  def like
    usr_id = 0;
    if request.headers['X-API-KEY'].nil?
      if params[:api_key].nil?
        usr_id = session[:user_id]
      else
        usr_id = params[:api_key]
      end
    else
      usr_id = request.headers['X-API-KEY']
    end
    @user = User.find_by(id: usr_id)
    if !usr_id.nil?
      @comment.liked_by @user
      @comment.user.karma += 1
      @comment.user.save
       respond_to do |format|
        format.html {redirect_to request.referrer}
        format.json { render :show, status: :ok, location: @comment  }
      end
    end
  end
  
  def unlike
    usr_id = 0;
    if request.headers['X-API-KEY'].nil?
      if params[:api_key].nil?
        usr_id = session[:user_id]
      else
        usr_id = params[:api_key]
      end
    else
      usr_id = request.headers['X-API-KEY']
    end
    @comment.unliked_by User.find_by(id: usr_id)
    @comment.user.karma -= 1
    @comment.user.save
    respond_to do |format|
      format.html {redirect_to request.referrer}
      format.json { render :show, status: :ok, location: @comment  }
    end
  end
  
  def add_reply
    @comment = Comment.find(params[:id]);
    @post = Post.find(@comment.post_id);
  end
 
  private
    # Use callbacks to share common setup or constraints between actions.
    def set_comment
      @comment = Comment.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def comment_params
      params.permit(:body, :post_id, :parent_id, :user_id)
    end
end
