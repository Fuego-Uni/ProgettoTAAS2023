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

export async function updateUser(name: string | undefined = undefined, email: string | undefined = undefined, pfp: File | undefined = undefined) {
  initiateAxios()
  
  const formData = new FormData();
  if (name) formData.append("name", name);
  // if (email) formData.append("email", email);
  if (pfp) formData.append("image", pfp);

  console.log(formData)

  axios.post(`http://localhost:8080/user/update`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then((res) => {
    console.log(res.data);
  }).catch((err) => {
    console.log(err.response.data);
  });
}