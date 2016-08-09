#Usage: rake deploy message="Message"
desc "Publishing the website via git"
task :deploy do
  system "git add -A"
  message = ENV["message"] || "Empty Message"
  system "git commit -m \"#{message}\""
  puts "Publishing to GitRep"
  system "git push origin develop"
  puts "Your website is now published"
end