import { useState, useEffect } from 'react'

export default function Toast({ message, type = 'success' }) {
  const [isVisible, setIsVisible] = useState(true)

  useEffect(() => {
    const timer = setTimeout(() => setIsVisible(false), 3000)
    return () => clearTimeout(timer)
  }, [])

  if (!isVisible) return null

  return (
    <div className={`toast show ${type}`}>
      {message}
    </div>
  )
}
