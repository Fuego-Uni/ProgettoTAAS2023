import axios from 'axios'
import type { UserInfo } from './types'
import { updateUserInfo } from './store/user_info_store'

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
  let response = await axios.get(
    'http://localhost:8080/user/get', { }
  )
    
  return response.data
}