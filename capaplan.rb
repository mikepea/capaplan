#!/usr/bin/env ruby

require 'rubygems'
require 'bundler/setup'

require 'digest/sha1'
require 'fileutils'
require 'timeout'

require 'thin'
require 'sinatra'

TMP_DIR = '/tmp/capaplan'
SHA1_1MB_ZEROS = '3b71f43ff30f4b15b5cd85dd9e95ebc7e84eb5a3'
SHA1_10MB_ZEROS = '8c206a1a87599f532ce68675536f0b1546900d7a'
SHA1_TIMEOUT = 0.1  # 100ms

unless File.exists?(TMP_DIR) then
  FileUtils.mkdir(TMP_DIR)
end

unless File.exists?("#{TMP_DIR}/10mb_zeros") then
  `dd if=/dev/zero of=#{TMP_DIR}/10mb_zeros bs=1024k count=10`
end

unless File.exists?("#{TMP_DIR}/1mb_zeros") then
  `dd if=/dev/zero of=#{TMP_DIR}/1mb_zeros bs=1024k count=1`
end

unless File.exists?("#{TMP_DIR}/10kb_zeros") then
  `dd if=/dev/zero of=#{TMP_DIR}/10kb_zeros bs=10k count=1`
end

#---------

get '/' do
  "Capaplan Capacity Planner\n  /status - give status"
end

get '/status' do
  "OK"
end

get '/load/cpu/sha1' do
  # compute the SHA1 of a 1mb of zeros
  # combined with buffer cache, this is essentially a CPU-only operation
  #  - though do we have stat() to content with?
  hex = ''
  timeout = params[:timeout].to_f || SHA1_TIMEOUT

  begin 
    Timeout.timeout(timeout) {
      hex = Digest::SHA1.file("#{TMP_DIR}/10mb_zeros").hexdigest  
    }
  rescue => e
    puts "Timeout executing SHA1 (timeout=#{timeout}) error='#{e.message}'"
    halt 500
  end

  if ( hex != SHA1_10MB_ZEROS ) then
    puts "SHA1 checksum does not match! (#{hex})"
    halt 500
  end

  "OK"

end

get '/load/noop/simple' do
  # basic minimal load result - pretty much return 200.
  # this is useful for comparing against raw application server performance
  # (to calculate ruby/sinatra overhead)
  "OK"
end

error do
  'Sorry there was a nasty error - ' + env['sinatra.error'].name
end

# vim: et ts=2 sw=2

