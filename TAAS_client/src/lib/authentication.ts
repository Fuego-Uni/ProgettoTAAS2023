import axios from 'axios'
import type { UserInfo } from './types'

let axios_initialized = false

export function setAuthenticationToken(token: string | null) {
  if (token === null) {
    localStorage.removeItem('auth-token')
    delete axios.defaults.headers.common['Authorization']
    return
  }
  localStorage.setItem('auth-token', token)

  axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('auth-token')}`
  axios.defaults.headers.common['Content-Type'] = 'application/json'
}

export function initiateAxios() {
  if (axios_initialized) return

  axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('auth-token')}`
  axios.defaults.headers.common['Content-Type'] = 'application/json'

  axios_initialized = true
}

export async function fetchUserInfo(): Promise<UserInfo> {
  let response = await axios.get(
    'http://localhost:8080/user/get', { }
  )
    
  return response.data
}