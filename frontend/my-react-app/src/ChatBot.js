import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

const BOT_AVATAR_URL = process.env.PUBLIC_URL + "/arijit pic.jpeg";
const USER_AVATAR_URL = process.env.PUBLIC_URL + "/you pic.jpeg";

function ChatBot() {
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState('');
  const [typing, setTyping] = useState(false);
  const chatRef = useRef(null);


  useEffect(() => {
    let sessionId = localStorage.getItem('chatSessionId');
    if (!sessionId) {
      sessionId = uuidv4();
      localStorage.setItem('chatSessionId', sessionId);
    }


    const fetchGreeting = async () => {
      try {
        const res = await axios.get(
          `https://my-resume-chatbot-production-e008.up.railway.app/api/chat/greeting?sessionId=${sessionId}`
        );
        if (res.data.trim()) {
          addBotMessage(res.data);
        }
      } catch (err) {
        console.error('Greeting error:', err);
      }
    };
    fetchGreeting();
  }, []);


  useEffect(() => {
    if (chatRef.current) {
      chatRef.current.scrollTop = chatRef.current.scrollHeight;
    }
  }, [messages]);

  const addUserMessage = (text) => {
    const timeStamp = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    setMessages((prev) => [...prev, { role: 'user', content: text, timestamp: timeStamp }]);
  };

  const addBotMessage = (text) => {
    const timeStamp = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    setMessages((prev) => [...prev, { role: 'bot', content: text, timestamp: timeStamp }]);
  };

  const handleSend = async () => {
    if (!userInput.trim()) return;
    addUserMessage(userInput);
    setTyping(true);

    try {
      const sessionId = localStorage.getItem('chatSessionId');
      const response = await axios.post(
        `https://my-resume-chatbot-production-e008.up.railway.app/api/chat/message?sessionId=${sessionId}`,
        { text: userInput }
      );
      addBotMessage(response.data || 'No response found.');
    } catch (error) {
      console.error('Error calling AI service:', error);
      addBotMessage('Error calling the AI service.');
    } finally {
      setTyping(false);
      setUserInput('');
    }
  };

  const handleStartOver = async () => {
    try {
      const sessionId = localStorage.getItem('chatSessionId');
      const response = await axios.post(
        `https://my-resume-chatbot-production-e008.up.railway.app/api/chat/message?sessionId=${sessionId}`,
        { text: 'start over' }
      );


      setMessages([
        {
          role: 'bot',
          content: response.data,
          timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
        }
      ]);
    } catch (err) {
      console.error('Start over error:', err);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleSend();
    }
  };

  return (
    <div style={styles.pageWrapper}>
      {/* The entire screen is this chat layout */}
      <div style={styles.header}>
        <img src={BOT_AVATAR_URL} alt="Bot avatar" style={styles.headerAvatar} />
        <div style={styles.headerTitle}>
          <h2 style={{ margin: 0, color: '#fff' }}>Arijit Ajay Kumar</h2>
          <span style={{ color: '#ccc' }}>Resume bot</span>
        </div>
      </div>

      <div style={styles.chatWindow} ref={chatRef}>
        {messages.map((msg, index) => {
          const isUser = msg.role === 'user';
          const avatarSrc = isUser ? USER_AVATAR_URL : BOT_AVATAR_URL;

          return (
            <div
              key={index}
              style={{
                display: 'flex',
                justifyContent: isUser ? 'flex-end' : 'flex-start',
                marginBottom: 16
              }}
            >
              {!isUser && (
                <img
                  src={avatarSrc}
                  alt="avatar"
                  style={{ ...styles.msgAvatar, marginRight: 8 }}
                />
              )}
              <div
                style={{
                  ...styles.bubble,
                  backgroundColor: isUser ? '#DAEBF7' : '#FFF'
                }}
              >
                <div style={styles.bubbleContent}>{msg.content}</div>
                <div style={styles.timestamp}>{msg.timestamp}</div>
              </div>
              {isUser && (
                <img
                  src={avatarSrc}
                  alt="avatar"
                  style={{ ...styles.msgAvatar, marginLeft: 8 }}
                />
              )}
            </div>
          );
        })}

        {/* Typing indicator */}
        {typing && (
          <div style={{ display: 'flex', marginBottom: 16 }}>
            <img
              src={BOT_AVATAR_URL}
              alt="bot"
              style={{ ...styles.msgAvatar, marginRight: 8 }}
            />
            <div style={{ ...styles.bubble, backgroundColor: '#FFF' }}>
              <div style={styles.bubbleContent}>...</div>
            </div>
          </div>
        )}
      </div>

      <div style={styles.inputBar}>
        <input
          style={styles.input}
          placeholder="Type your message here..."
          value={userInput}
          onChange={(e) => setUserInput(e.target.value)}
          onKeyDown={handleKeyDown}
        />
        <button style={styles.sendButton} onClick={handleSend}>Send</button>
        <button style={styles.startOverButton} onClick={handleStartOver}>Start Over</button>
      </div>
    </div>
  );
}

const styles = {

  pageWrapper: {
    margin: 0,
    padding: 0,
    width: '100vw',
    height: '100vh',
    backgroundColor: '#2B3847',
    display: 'flex',
    flexDirection: 'column',
    boxSizing: 'border-box',
  },
  header: {
    display: 'flex',
    alignItems: 'center',
    backgroundColor: '#2B3847',
    padding: '10px 20px',
    flexShrink: 0,
    borderBottom: '1px solid #394B5A',
  },
  headerAvatar: {
    width: 48,
    height: 48,
    borderRadius: '50%',
    marginRight: 12,
  },
  headerTitle: {
    display: 'flex',
    flexDirection: 'column',
  },
  chatWindow: {
    flex: 1,
    overflowY: 'auto',
    padding: 20,
  },
  msgAvatar: {
    width: 40,
    height: 40,
    borderRadius: '50%',
  },
  bubble: {
    maxWidth: '60%',
    borderRadius: 8,
    padding: 10,
    boxShadow: '0px 2px 6px rgba(0,0,0,0.15)',
    display: 'flex',
    flexDirection: 'column',
  },
  bubbleContent: {
    fontSize: 15,
    color: '#333',
  },
  timestamp: {
    marginTop: 6,
    fontSize: 12,
    color: '#999',
    textAlign: 'right',
  },
  inputBar: {
    display: 'flex',
    padding: '10px 20px',
    backgroundColor: '#2B3847',
    borderTop: '1px solid #394B5A',
  },
  input: {
    flex: 1,
    padding: 10,
    borderRadius: 4,
    border: '1px solid #ccc',
    fontSize: 14,
    marginRight: 8,
    outline: 'none',
  },
  sendButton: {
    backgroundColor: '#007BFF',
    color: '#fff',
    border: 'none',
    borderRadius: 4,
    padding: '10px 16px',
    fontSize: 14,
    cursor: 'pointer',
  },
  startOverButton: {
    backgroundColor: '#DC3545',
    color: '#fff',
    border: 'none',
    borderRadius: 4,
    padding: '10px 16px',
    fontSize: 14,
    cursor: 'pointer',
    marginLeft: 8,
  },
};

export default ChatBot;
