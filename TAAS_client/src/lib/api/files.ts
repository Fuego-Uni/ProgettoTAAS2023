import { initiateAxios } from "$lib/authentication";
import { getUserInfo } from "$lib/store/user_info_store";
import axios from "axios";

export async function getProfilePicture(): Promise<string | null> {
  initiateAxios();

  try {
    const res = await axios.get('http://localhost:8080/storage/pfp', {
      responseType: 'blob' // tell axios to treat the response as a blob
    });

    const reader = new FileReader();
    reader.readAsDataURL(res.data); // convert the blob to a data URL

    return new Promise((resolve, reject) => {
      reader.onloadend = () => {
        resolve(reader.result as string)
      }
    })
  } catch (e) {
    return null;
  }
}

export async function getProfilePictureFriend(user: string): Promise<string | null> {
  initiateAxios();

  try {
    const res = await axios.get(`http://localhost:8080/storage/pfp/friend`, {
      params: { user },
      responseType: 'blob' // tell axios to treat the response as a blob
    });

    const reader = new FileReader();
    reader.readAsDataURL(res.data); // convert the blob to a data URL

    return new Promise((resolve, reject) => {
      reader.onloadend = () => {
        resolve(reader.result as string)
      }
    })
  } catch (e) {
    return null;
  }
}

export async function postProfilePicture(file: File) {
  initiateAxios();

  const formData = new FormData();
  formData.append('file', file);

  await axios.post('http://localhost:8080/storage/pfp', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}