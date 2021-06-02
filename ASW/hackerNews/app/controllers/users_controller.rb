class UsersController < ApplicationController
  before_action :set_user, only: [:show, :edit, :update, :destroy]
  def omniauth
    user = User.from_omniauth(request.env['omniauth.auth'])
    if user.valid?
        session[:user_id] = user.id
        redirect_to user_path(user)
    else
        redirect_to '/login'
    end
  end
  # GET /users
  # GET /users.json
  def index
    @users = User.all

  end

  # GET /users/1
  # GET /users/1.json
  def show
    @user = User.find(params[:id])
  end

  # GET /users/new
  def new
    @user = User.new
  end

  # GET /users/1/edit
  def edit
  end
  
  
  
  #GET /users/1/submissions
  def submissions
    @posts = Post.where(user_id: params[:id])
  end
  
  #GET /users/1/upvoted_submissions
  def upvoted_submissions
    usr_id = 0;
    if request.headers['X-API-KEY'].nil?
      usr_id = params[:id]
    else
      usr_id = request.headers['X-API-KEY']
    end
    @posts = User.find(usr_id).get_voted Post
  end
  
  #GET /users/1/comments
  def comments
    @comments = Comment.where(user_id: params[:id])
  end
  
  #GET /users/1/upvoted_comments
  def upvoted_comments
    usr_id = 0;
    if request.headers['X-API-KEY'].nil?
      usr_id = params[:id]
    else
      usr_id = request.headers['X-API-KEY']
    end
    @user = User.find(usr_id)
    @comments = @user.get_voted Comment
  end
  

  # POST /users
  # POST /users.json
  def create
    @user = User.new(user_params)
    
    if @user.save
      format.html { redirect_to @user, notice: 'User was successfully created.' }
      format.json { render :show, status: :created, location: @user }
    else
      format.html { render :new }
      format.json { render json: @user.errors, status: :unprocessable_entity }
    end
  end

  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
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
    @user = User.find(usr_id)
    
    if @user.update(params.permit(:email, :about))
      format.html { redirect_to @user, notice: 'User was successfully updated.' }
      format.json { render :show, status: :ok, location: @user }
    else
      format.html { render :edit }
      format.json { render json: @user.errors, status: :unprocessable_entity }
    end
    
  end

  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    log_out
    redirect_to root_url
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def user_params
      params.require(:user).permit(:name, :about, :email, :password)
    end
    
  
end
