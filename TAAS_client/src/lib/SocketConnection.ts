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
    this.socket = new WebSocket(`wss://localhost:8080/notification/socket`);
    this.handlers = {};

    this.socket.onopen = () => {
      // console.log("socket opened")

      this.send({
        message: 'register-client',
        data: 'tomm2000'
      })
    };

    this.socket.onclose = () => {
      // console.log("socket closed")
    };

    this.socket.onmessage = (event) => {
      const data = JSON.parse(event.data);

      let handlers = this.handlers[data.message];

      if(handlers) {
        for(let handler_id in handlers) {
          handlers[handler_id](data.data);
        }
      } else {
        console.log("no handler for message", data.message)
      }
    }
  }

  setOnMessage(message: string, id: string, callback: (data: any) => void) {
    if(!this.handlers[message]) {
      this.handlers[message] = {};
    }

    this.handlers[message][id] = callback;

    // console.table(this.handlers)
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

export function closeMainSocket() {
  if (main_socket) {
    main_socket.disconnect();
    main_socket = null;
  }
}

export function mainSocketSetHandler(message: string, id: string, handler: MessageHandler) {
  if(!main_socket) {
    main_socket = new SocketConnection();
  }

  if (main_socket) {
    main_socket.setOnMessage(message, id, handler);
  }
}