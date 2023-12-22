export async function load({ params }) {
  const chat_id = `${params.chat}`
  return {
    chat_id
  }
}