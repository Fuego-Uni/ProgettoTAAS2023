export type SocketMessage = {
  message: string,
  data: any
}

export type MessageHandler = (data: any) => void;

export class SocketConnection {
  private socket: WebSocket | null = null;
  private handlers: {
    [key: string]: { // each string is a message type
      [key: string]: MessageHandler // each string is a handler id
    }
  } = {};

  constructor() {
    this.connect()
  }

  connect() {
    this.socket = new WebSocket(`ws://localhost:8080/notification/socket?=${localStorage.getItem('auth-token')}`);
    this.handlers = {};

    this.socket.onopen = () => {
      console.log("socket opened");
    };

    this.socket.onclose = () => {
      console.log("socket closed")
    };

    this.socket.onmessage = (event) => {
      console.log("socket message", event.data);

      const data = JSON.parse(event.data);

      let handlers = this.handlers[data.message];

      if(handlers) {
        for(let handler_id in handlers) {
          handlers[handler_id](data.data);
        }
      } else {
        console.log(`No handler for message "${data.message}"`)
      }
    }
  }

  setOnMessage(message: string, callback: (data: any) => void, id: string = "default") {
    if(!this.handlers[message]) {
      this.handlers[message] = {};
    }

    this.handlers[message][id] = callback;
  }

  send(message: SocketMessage) {
    this.socket?.send(JSON.stringify(message));
  }

  disconnect() {
    if (this.socket) {
      this.socket.close();

      this.socket = null;
    }
  }
}


let main_socket: SocketConnection | null = null;

export function createMainSocket() {
  if(!main_socket) {
    main_socket = new SocketConnection();
  }
}

export function closeMainSocket() {
  if (main_socket) {
    main_socket.disconnect();
    main_socket = null;
  }
}

export function mainSocketSetHandler(message: string, handler: MessageHandler, id: string = "default") {
  if(!main_socket) {
    main_socket = new SocketConnection();
  }

  if (main_socket) {
    main_socket.setOnMessage(message, handler, id);
  }
}