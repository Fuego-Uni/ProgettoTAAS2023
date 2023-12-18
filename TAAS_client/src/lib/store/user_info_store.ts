// film list 
import { fetchUserInfo } from '$lib/authentication';
import type { UserInfo } from '$lib/types';
import { writable } from 'svelte/store';

// Initialize the store with an empty array
let _user: UserInfo | null = null;
export const user = writable<UserInfo | null>(_user);

user.subscribe((value) => {
  _user = value;
});

export async function getUserInfo() {
  if (_user == null) {
    try {
      user.set(await fetchUserInfo());
    } catch (err) {
      user.set(null);
    }
  }

  return _user;
}