import axios from "axios"

/* ADD CHAT */
export const addChat =async (user2_email: string): Promise<String> => {
    return axios.post('http://localhost:8080/chat/add/chat',
       { params: {
            email: user2_email,
        }
    }
    )
}


