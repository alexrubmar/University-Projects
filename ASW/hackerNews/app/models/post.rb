class Post < ApplicationRecord
    belongs_to :user #pertenecera a usuarios cuando implementemos logins
    has_many :comments
    #validaremos cuando implementemos usuarios
    #validates_presence_of :user_id, :message => "you have to log in to create a submission"  
    acts_as_votable
end
