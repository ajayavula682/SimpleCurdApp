import { useState, useEffect } from 'react'
import { userApi } from '../services/api'
import UserModal from './UserModal'
import Toast from './Toast'

export default function Users() {
  const [users, setUsers] = useState([])
  const [filteredUsers, setFilteredUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editingUser, setEditingUser] = useState(null)
  const [searchTerm, setSearchTerm] = useState('')
  const [statusFilter, setStatusFilter] = useState('')
  const [toast, setToast] = useState(null)

  useEffect(() => {
    loadUsers()
  }, [])

  useEffect(() => {
    filterUsers()
  }, [users, searchTerm, statusFilter])

  async function loadUsers() {
    try {
      setLoading(true)
      const response = await userApi.getAll()
      setUsers(response.data)
    } catch (error) {
      showToast('Failed to load users', 'error')
    } finally {
      setLoading(false)
    }
  }

  function filterUsers() {
    let result = users

    if (searchTerm) {
      result = result.filter(u =>
        u.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        u.email?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        u.phone?.includes(searchTerm)
      )
    }

    if (statusFilter) {
      const isActive = statusFilter === 'active'
      result = result.filter(u => u.active === isActive)
    }

    setFilteredUsers(result)
  }

  async function handleSave(formData) {
    try {
      if (editingUser) {
        await userApi.update(editingUser.id, formData)
        showToast('User updated successfully')
      } else {
        await userApi.create(formData)
        showToast('User created successfully')
      }
      setShowModal(false)
      setEditingUser(null)
      loadUsers()
    } catch (error) {
      const message = error.response?.data?.message || 'Failed to save user'
      showToast(message, 'error')
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Are you sure you want to delete this user?')) return

    try {
      await userApi.delete(id)
      showToast('User deleted successfully')
      loadUsers()
    } catch (error) {
      showToast('Failed to delete user', 'error')
    }
  }

  async function handleToggleStatus(user) {
    try {
      if (user.active) {
        await userApi.deactivate(user.id)
        showToast('User deactivated')
      } else {
        await userApi.activate(user.id)
        showToast('User activated')
      }
      loadUsers()
    } catch (error) {
      showToast('Failed to update user status', 'error')
    }
  }

  function handleEdit(user) {
    setEditingUser(user)
    setShowModal(true)
  }

  function showToast(message, type = 'success') {
    setToast({ message, type })
    setTimeout(() => setToast(null), 3000)
  }

  return (
    <div>
      <div className="section-header">
        <h2>User Management</h2>
        <button className="btn btn-primary" onClick={() => {
          setEditingUser(null)
          setShowModal(true)
        }}>
          + Add User
        </button>
      </div>

      <div className="filters">
        <input
          type="text"
          placeholder="Search users..."
          className="search-input"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select
          className="filter-select"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value="">All Users</option>
          <option value="active">Active</option>
          <option value="inactive">Inactive</option>
        </select>
      </div>

      <div className="table-container">
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Address</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="7" className="loading">Loading...</td></tr>
            ) : filteredUsers.length === 0 ? (
              <tr><td colSpan="7" className="loading">No users found</td></tr>
            ) : (
              filteredUsers.map(user => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.phone}</td>
                  <td>{user.address}</td>
                  <td>{user.active ? '✓ Active' : '✗ Inactive'}</td>
                  <td>
                    <div className="actions">
                      <button
                        className="btn btn-warning"
                        onClick={() => handleEdit(user)}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-warning"
                        onClick={() => handleToggleStatus(user)}
                      >
                        {user.active ? 'Deactivate' : 'Activate'}
                      </button>
                      <button
                        className="btn btn-danger"
                        onClick={() => handleDelete(user.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <UserModal
          user={editingUser}
          onClose={() => setShowModal(false)}
          onSave={handleSave}
        />
      )}

      {toast && <Toast message={toast.message} type={toast.type} />}
    </div>
  )
}
