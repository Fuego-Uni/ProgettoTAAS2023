import axios from "axios"
import type { MessageType } from "./types";
import { initiateAxios } from "./authentication";

/* ADD CHAT */
export const addChat = async (user2_email: string): Promise<string> => {
  initiateAxios();

  return axios.post('http://localhost:8080/chat/add', { email: user2_email })
    .then((response) => {
      console.log(response.data);
      return response.data;
    });
}

export const getChat = async (): Promise<any> => {
  initiateAxios();

  return axios.get('http://localhost:8080/chat/get', { })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error(error);
    });
}

export const getMessageByChatId = async (chat_id: string): Promise<string> => {
  initiateAxios();

  return axios.get('http://localhost:8080/chat/get/message', { params: { chat_id: chat_id } })
    .then((response) => {
      // parse the json response.data 
      console.log(response.data.messages);
      return response.data;
    });
}

export const addMessagetToChat = async (message: MessageType): Promise<string> => {
  initiateAxios();

  return axios.post('http://localhost:8080/chat/add/message', {
    message: message.message,
    chatId: message.chat_id
  })
    .then((response) => {
      console.log(response.data);
      return response.data;
    });
}
