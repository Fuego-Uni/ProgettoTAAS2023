export async function load({ params }) {
  const film_id = `${params.filmId}`
  return {
    film_id
  }
}