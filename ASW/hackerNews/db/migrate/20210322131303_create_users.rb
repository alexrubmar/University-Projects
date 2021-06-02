class CreateUsers < ActiveRecord::Migration[6.1]
  def change
    create_table :users do |t|
      t.string :name
      t.string :about
      t.string :email
      t.string :password
      t.integer :karma, :default => 0

      t.timestamps
    end
  end
end
