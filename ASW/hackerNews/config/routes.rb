Rails.application.routes.draw do
  
  get 'users/:id/comments' => 'users#comments'
  get 'users/:id/upvoted_comments' => 'users#upvoted_comments'
  
  resources :comments do
    member do
      get "like"
      get "unlike"
      post "like"
      post "unlike"
    end
  end
  
  get 'posts/index_new' => 'posts#index_new'
  get 'posts/index_ask' => 'posts#index_ask'
  get 'comments/:id/add_reply' => 'comments#add_reply'
  post '/posts' => 'posts#create'
  
  post '/posts/comments' => 'comments#create'
  
  resources :posts do
    member do
      get "like"
      get "unlike"
      post "like"
      post "unlike"
    end
    resources :comments
  end
  
  resources :users do
    resources :comments
  end
  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root 'posts#index'
  
  get 'auth/:provider/callback' => 'users#omniauth'
  get 'signout' => 'sessions#destroy', :as => :signout
  get 'users/:id/submissions' => 'users#submissions'
  get 'users/:id/upvoted_submissions' => 'users#upvoted_submissions'

end
