class CreatePosts < ActiveRecord::Migration[6.1]
  def change
    create_table :posts do |t|
      t.string :type
      t.string :title
      t.string :text
      t.string :url
      t.integer :user_id

      t.timestamps
    end
  end
end
