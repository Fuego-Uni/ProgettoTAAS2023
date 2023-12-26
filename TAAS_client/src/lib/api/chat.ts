import axios from "axios"
import type { ChatType, MessageType } from "../types";
import { initiateAxios } from "../authentication";
import { getUserInfo } from "$lib/store/user_info_store";

/* ADD CHAT */
export const addChat = async (user2_email: string): Promise<string> => {
  initiateAxios();

  return axios.post('http://localhost:8080/chat/add', { email: user2_email })
    .then((response) => {
      console.log(response.data);
      return response.data;
    })
    .catch((error) => {
      // console.error(error);
      // remove stack trace
      delete error.stack

      console.error(error);
    }); 
}

export const getChat = async (): Promise<ChatType[]> => {
  initiateAxios();

  return axios.get('http://localhost:8080/chat/get', { })
    .then((response) => {
      return response.data
    })
    .catch((error) => {
      console.error(error.message);
    });
}

export const getMessagesByChatId = async (chat_id: string): Promise<MessageType[]> => {
  initiateAxios();

  return axios.get('http://localhost:8080/chat/get/message', { params: { chat: chat_id } })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error.message);
    });
}

export const addMessagetToChat = async (message: string, chat: string, file: File | undefined = undefined): Promise<string> => {
  initiateAxios();

  return axios.post('http://localhost:8080/chat/add/message', {
    message,
    chatId: chat,
    image: file
  }, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
    .then((response) => {
      console.log(response.data);
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });
}
