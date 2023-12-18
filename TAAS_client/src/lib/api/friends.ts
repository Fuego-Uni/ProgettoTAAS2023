import type { UserInfo } from "$lib/types";
import axios from "axios";

export async function addFriend(email: string) {
  axios.post(`http://localhost:8080/user/friend/add`, {
    friend: email
  }).then((res) => {
    console.log(res);
  }).catch((err) => {
    console.log(err.response.data);
  });
}

export async function getFriendList(): Promise<string[]> {
  let friends = await axios.get(`http://localhost:8080/user/friend/all`)
  
  return friends.data;
}

export function removeFriend(email: any) {
  axios.post(`http://localhost:8080/user/friend/remove`, {
    friend: email
  }).then((res) => {
    console.log(res);
  }).catch((err) => {
    console.log(err.response.data);
  });
}

export async function getFriendInfo(email: string): Promise<UserInfo> {
  let friend = await axios.get(`http://localhost:8080/user/friend/info`, { params: { email }})
  
  return friend.data;
}