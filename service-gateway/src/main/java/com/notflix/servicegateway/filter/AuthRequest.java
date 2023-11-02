package com.notflix.servicegateway.filter;

public class AuthRequest {

 public String email;
 public String name;
 
 public AuthRequest(String email, String name) {
  this.email = email;
  this.name = name;
 }
 //get setter
 public String getEmail() {
  return email;
 }
 public void setEmail(String email) {
  this.email = email;
 }
 public String getName() {
  return name;
 }
 public void setName(String name) {
  this.name = name;
 }
}
