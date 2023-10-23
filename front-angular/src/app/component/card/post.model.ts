export interface Comment {
  id: number;
  picsId: number;
  userId: number;
  comment: string;
  username: string;
}


export interface User {
  id: number;
  username: string;
}

export interface Post {
  user_id: string;
  image: string;
  subtitle: string;
  urlImage: string;
  id: number;
  comments: Comment[];
  user: User[];
}