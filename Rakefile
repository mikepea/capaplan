
task :bundler do |t|
  sh 'jruby -S gem install bundler'
end

task :bundle => [:bundler] do |t|
  sh 'jruby -S bundle install'
end

