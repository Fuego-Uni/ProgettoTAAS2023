export type ContentPreviewData = {
  banner: string,
  title: string,
  date: string,
  rating_average: number,
  rating_personal: number,
}

export type UserInfo = {
  name: string,
  email: string,
  role: string,
}

export type MediaData = {
  id: number,
  title: string,
  overview: string,
  release_date: string,
  poster_path: string,
  backdrop_path: string,
  rating_average: number,
  vote_count: number,
}

export type Review = {
  vote: number;
  note: string;
  user: any;
}

export type MessageType = {
  content: string;
  email: string;
  timestamp: string;
}

export type ChatType = {
  id: string;
  user1: string;
  user2: string;
}