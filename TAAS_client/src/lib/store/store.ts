// film list 
import { writable } from 'svelte/store';
import type { FilmData } from "../types"



// Initialize the store with an empty array
export const filmStore = writable<FilmData[]>([]);
// USAGE
/* // To set the store to a new array of films
filmStore.set(newFilms);

// To update the store based on its current state
filmStore.update(currentFilms => currentFilms.concat(newFilm));

// To subscribe to changes in the store
const unsubscribe = filmStore.subscribe(films => {
  console.log(films);
});

// Remember to unsubscribe when you're done to prevent memory leaks
unsubscribe(); */