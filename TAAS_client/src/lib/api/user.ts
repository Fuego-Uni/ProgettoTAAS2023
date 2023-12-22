import { initiateAxios } from "$lib/authentication";
import type { UserInfo } from "$lib/types";
import axios from "axios";

export async function fetchtUserInfo(): Promise<UserInfo> {
  initiateAxios()

  return axios.get(`http://localhost:8080/user/get`, {})
  .then((res) => {
    return res.data;
  }).catch((err) => {
    console.log(err.response.data);
  });
}