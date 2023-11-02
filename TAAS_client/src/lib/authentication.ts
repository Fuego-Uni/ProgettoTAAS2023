import axios from 'axios'

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
}