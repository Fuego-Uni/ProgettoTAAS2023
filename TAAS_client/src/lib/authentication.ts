import axios from 'axios'
import type { UserInfo } from './types'

export function setAuthenticationToken(token: string | null) {
  if (token === null) {
    localStorage.removeItem('auth-token')
    delete axios.defaults.headers.common['Authorization']
    return
  }
  localStorage.setItem('auth-token', token)
}

export function initiateAxios() {
  axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('auth-token')}`
  axios.defaults.headers.common['Content-Type'] = 'application/json'
}

export async function fetchUserInfo(): Promise<UserInfo> {
  return axios.get(
    'http://localhost:8080/user/me', { }
  )
    .then((response) => {
      console.log(response.data)
    }).catch((err) => {
      console.error("Unable to get users")
    })
}