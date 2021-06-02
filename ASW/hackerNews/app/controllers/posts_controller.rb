class PostsController < ApplicationController
  before_action :set_post, only: [:show, :edit, :update, :destroy, :like, :unlike]

  # GET /posts
  # GET /posts.json
  def index
    @posts = Post.order('cached_votes_total DESC')
  end
  
  #GET /posts/index_new
  def index_new
    @posts = Post.order('created_at DESC');
  end
  
  def index_ask
    @posts = Post.order('cached_votes_total DESC');
  end

  # GET /posts/1
  # GET /posts/1.json
  def show
    @post = Post.find(params[:id])
    @comments = Comment.where(post_id: @post.id)
  end
  

  # GET /posts/new
  def new
    
  end

  # GET /posts/1/edit
  def edit
  end

  # POST /posts
  # POST /posts.json
  def create
    puts params[:title].nil?;
    title = params[:title];
    postUrl = params[:url];
    postText = params[:text];
    usr_id = params[:api_key];
    puts "USER ID IS:"
    puts usr_id
    
    #if request.headers['X-API-KEY'].nil?
     # usr_id = session[:user_id]
    #else
     # usr_id = id_usr
    #end
    @user = User.find_by(id: usr_id)
    puts "llego2"
    if @user.nil?
      redirect_to "/users/new"
    else
      if (!title.blank? && (!postUrl.blank? || !postText.blank?))
        if postUrl.blank?
          #@post = AskPost.new(params.permit(:title, :text))
          @post = Post.new(params.permit(:title, :text, :createdAt, :updatedAt))
          @post.url = "";
          @post.user_id = usr_id;
          @post = @user.posts.new(post_params)
          @post.user = User.find_by(id: usr_id)
          respond_to do |format|
            if @post.save
              format.html { redirect_to "/posts/index_new", notice: 'Post was successfully created.' }
              format.json { render :show, status: :created, location: @post }
            else
              format.html { render :new }
              format.json { render json: @post.errors, status: :unprocessable_entity }
            end
          end

        else
          if postUrl.start_with?('https://')
            postUrl.slice! "https://"
          end
          if postUrl.start_with?('http://')
            postUrl.slice! "http://"
          end
          exists = Post.where(url: postUrl).exists?(conditions = :none)
          if exists != true
            
            postAsk = params[:text];
            if !postAsk.blank?
              
              @post = Post.new(params.permit(:title, :url, :createdAt, :updatedAt))
              @post.user_id = usr_id;
              @post = @user.posts.new(post_params)
              @post.user = User.find_by(id: usr_id)
              #@comment = @post.comments.new(parent_id: params[:parent_id])
              #@post.comments = Comment.find_by(post_id: params[:id])
              
            else
              @post = Post.new(params.permit(:title, :url, :createdAt, :updatedAt))
              @post.user_id = usr_id;
              @post = @user.posts.new(post_params)
              @post.user = User.find_by(id: usr_id)
            end
          else
            @post = Post.where(url: postUrl)[0]
          end
          respond_to do |format|
            if !exists
              if @post.save
                format.html { redirect_to "/posts/index_new", notice: 'Post was successfully created.' }
                format.json { render :show, status: :created, location: @post }
              else
                format.html { render :new }
                format.json { render json: @post.errors, status: :unprocessable_entity }
              end
            else
              format.html { redirect_to "/posts/#{@post.id}", notice: 'Post was redirected.' }
              format.json { render :show, status: :created, location: @post }
            end
          end
        end
      else
        
      end
    end
  end

  # PATCH/PUT /posts/1
  # PATCH/PUT /posts/1.json
  def update
    respond_to do |format|
      if @post.update(post_params)
        format.html { redirect_to @post, notice: 'Post was successfully updated.' }
        format.json { render :show, status: :ok, location: @post }
      else
        format.html { render :edit }
        format.json { render json: @post.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /posts/1
  # DELETE /posts/1.json
  def destroy
    @post.destroy
    respond_to do |format|
      format.html { redirect_to posts_url, notice: 'Post was successfully destroyed.' }
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
      @post.liked_by @user
      @post.user.karma += 1
      @post.user.save
       respond_to do |format|
        format.html {redirect_to request.referrer}
        format.json { render :show, status: :ok, location: @post  }
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
    @post.unliked_by User.find_by(id: usr_id)
    @post.user.karma -= 1
    @post.user.save
    respond_to do |format|
      format.html {redirect_to request.referrer}
      format.json { render :show, status: :ok, location: @post }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_post
      @post = Post.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def post_params
      params.permit(:title, :url, :text, :user_id)
    end
end
