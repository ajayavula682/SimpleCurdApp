# React CRUD Frontend

A modern React application for managing Users and Products with a clean UI.

## Features

- **Product Management**: Create, read, update, and delete products
- **User Management**: Create, read, update, and delete users
- **Search & Filter**: Search products by name/description and users by name/email/phone
- **Category Filter**: Filter products by category
- **Status Toggle**: Activate/deactivate users
- **Responsive Design**: Works on desktop and mobile devices

## Setup & Installation

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

The app will be available at `http://localhost:5173`

## API Configuration

The app is configured to connect to a backend API running at `http://localhost:8080`. Modify the `API_BASE` in `src/services/api.js` if your backend runs on a different port.

## Project Structure

```
src/
├── main.jsx              # Entry point
├── App.jsx               # Main app component
├── App.css               # Global styles
├── components/
│   ├── Products.jsx      # Products list & management
│   ├── ProductModal.jsx  # Product form modal
│   ├── Users.jsx         # Users list & management
│   ├── UserModal.jsx     # User form modal
│   └── Toast.jsx         # Toast notifications
└── services/
    └── api.js            # API client & endpoints
```

## Technologies

- **React 19** - UI library
- **Vite** - Build tool & dev server
- **Axios** - HTTP client
- **CSS3** - Styling

## Available Endpoints

**Users:**
- GET `/api/users` - Get all users
- POST `/api/users` - Create user
- PUT `/api/users/:id` - Update user
- DELETE `/api/users/:id` - Delete user
- GET `/api/users/search?keyword=` - Search users
- PATCH `/api/users/:id/activate` - Activate user
- PATCH `/api/users/:id/deactivate` - Deactivate user

**Products:**
- GET `/api/products` - Get all products
- POST `/api/products` - Create product
- PUT `/api/products/:id` - Update product
- DELETE `/api/products/:id` - Delete product
- GET `/api/products/search?keyword=` - Search products
- GET `/api/products/categories` - Get all categories
- GET `/api/products/category/:name` - Get products by category
