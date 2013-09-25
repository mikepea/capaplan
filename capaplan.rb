#!/usr/bin/env ruby

require 'rubygems'
require 'bundler/setup'

require 'sinatra'

get '/status' do
  "OK"
end
