import React, { useState, useEffect, useRef, useCallback } from 'react';
import { 
  Box, 
  FormControl, 
  InputLabel, 
  MenuItem, 
  Select, 
  Button, 
  Typography, 
  Grid, 
  Paper, 
  CircularProgress, 
  Divider, 
  Chip,
  Switch, 
  FormControlLabel,
  useMediaQuery,
  useTheme,
  IconButton,
  Tooltip,
  Alert
} from '@mui/material';
import ExploreIcon from '@mui/icons-material/Explore';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import PinDropIcon from '@mui/icons-material/PinDrop';
import MyLocationIcon from '@mui/icons-material/MyLocation';
import RouteIcon from '@mui/icons-material/Route';
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import InfoIcon from '@mui/icons-material/Info';
import BugReportIcon from '@mui/icons-material/BugReport';
import NavigationIcon from '@mui/icons-material/Navigation';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

import {
  findShortestPath,
  generateDirections,
  getAllRooms,
  calculateDistance,
  getNodeConnections
} from '../utils/navigationUtils';
import './NavigationMap.css';

// Import floor plan for reference but not pathing display
import floorPlanImg from '../assets/floor-plan-nodes.png';

// Compass component for direction reference
const CompassRose = () => {
  return (
    <Box sx={{ 
      position: 'absolute', 
      top: 8, 
      right: 8, 
      bgcolor: 'rgba(255,255,255,0.9)', 
      borderRadius: '50%',
      width: 65, 
      height: 65, 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center',
      boxShadow: '0 3px 8px rgba(0,0,0,0.15)',
      zIndex: 10,
      transition: 'transform 0.2s ease',
      '&:hover': {
        transform: 'scale(1.05)',
      }
    }}>
      <Box sx={{ position: 'relative', width: '100%', height: '100%' }}>
        <Typography 
          sx={{ 
            position: 'absolute', 
            top: -2, 
            left: '50%', 
            transform: 'translateX(-50%)',
            fontSize: '0.8rem',
            fontWeight: 'bold',
            color: '#1976d2'
          }}
        >
          N
        </Typography>
        <Typography 
          sx={{ 
            position: 'absolute', 
            bottom: -2, 
            left: '50%', 
            transform: 'translateX(-50%)',
            fontSize: '0.8rem',
            fontWeight: 'bold'
          }}
        >
          S
        </Typography>
        <Typography 
          sx={{ 
            position: 'absolute', 
            left: -2, 
            top: '50%', 
            transform: 'translateY(-50%)',
            fontSize: '0.8rem',
            fontWeight: 'bold'
          }}
        >
          W
        </Typography>
        <Typography 
          sx={{ 
            position: 'absolute', 
            right: -2, 
            top: '50%', 
            transform: 'translateY(-50%)',
            fontSize: '0.8rem',
            fontWeight: 'bold'
          }}
        >
          E
        </Typography>
        
        <Box sx={{ 
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 40,
          height: 40,
          borderRadius: '50%',
          border: '1px solid #ddd',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          background: 'rgba(240, 240, 240, 0.5)'
        }}>
          <ArrowUpwardIcon 
            sx={{ 
              position: 'absolute', 
              top: -2, 
              left: '50%', 
              transform: 'translateX(-50%)',
              color: '#1976d2',
              fontSize: '1.1rem'
            }} 
          />
          <ArrowDownwardIcon 
            sx={{ 
              position: 'absolute', 
              bottom: -2, 
              left: '50%', 
              transform: 'translateX(-50%)',
              fontSize: '1.1rem'
            }} 
          />
          <ArrowBackIcon 
            sx={{ 
              position: 'absolute', 
              left: -2, 
              top: '50%', 
              transform: 'translateY(-50%)',
              fontSize: '1.1rem'
            }} 
          />
          <ArrowForwardIcon 
            sx={{ 
              position: 'absolute', 
              right: -2, 
              top: '50%', 
              transform: 'translateY(-50%)',
              fontSize: '1.1rem'
            }} 
          />
        </Box>
      </Box>
    </Box>
  );
};

// Room marker component to show selected rooms
const RoomMarker = ({ room, type, dimension, onClick }) => {
  const isStart = type === 'start';
  
  return (
    <Box
      sx={{
        position: 'absolute',
        left: (room.x / dimension.width) * 100 + '%',
        top: (room.y / dimension.height) * 100 + '%',
        width: isStart ? 18 : 16,
        height: isStart ? 18 : 16,
        marginLeft: isStart ? -9 : -8,
        marginTop: isStart ? -9 : -8,
        bgcolor: isStart ? '#4caf50' : '#f44336',
        border: '2px solid white',
        borderRadius: '50%',
        boxShadow: '0 2px 6px rgba(0,0,0,0.3)',
        zIndex: 20,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        cursor: 'pointer',
        transition: 'transform 0.2s ease',
        '&:hover': {
          transform: 'scale(1.2)',
        },
        '@media (max-width: 600px)': {
          width: isStart ? 16 : 14,
          height: isStart ? 16 : 14,
          marginLeft: isStart ? -8 : -7,
          marginTop: isStart ? -8 : -7,
        }
      }}
      onClick={onClick}
    >
      {isStart ? (
        <MyLocationIcon sx={{ color: 'white', fontSize: '0.8rem' }} />
      ) : (
        <LocationOnIcon sx={{ color: 'white', fontSize: '0.8rem' }} />
      )}
    </Box>
  );
};

const NavigationMap = () => {
  // Theme and media query for responsive design
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  
  // State for start and end node selection
  const [startNode, setStartNode] = useState('');
  const [endNode, setEndNode] = useState('');
  
  // State for all available rooms
  const [rooms, setRooms] = useState([]);
  
  // State for the current path
  const [currentPath, setCurrentPath] = useState(null);
  
  // State for loading indicator
  const [loading, setLoading] = useState(false);
  const [initialLoading, setInitialLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // State for debugging information
  const [debug, setDebug] = useState(false);
  const [debugInfo, setDebugInfo] = useState(null);
  
  // Refs for the map container (canvas will be hidden)
  const mapContainerRef = useRef(null);
  
  // State for the image dimensions
  const [imageDimensions, setImageDimensions] = useState({ width: 0, height: 0 });
  
  // Load rooms on component mount
  useEffect(() => {
    try {
      setInitialLoading(true);
      const roomNodes = getAllRooms();
      setRooms(roomNodes);
      setInitialLoading(false);
    } catch (err) {
      setError('Failed to load navigation data');
      setInitialLoading(false);
      console.error('Navigation error details:', err);
    }
  }, []);
  
  // Function to handle image load and get dimensions
  const handleImageLoad = (e) => {
    const { width, height } = e.target;
    setImageDimensions({ width, height });
  };
  
  // Function to handle start node selection
  const handleStartNodeChange = (event) => {
    setStartNode(event.target.value);
    // Clear the current path when changing nodes
    setCurrentPath(null);
    setDebugInfo(null);
    // If end node is already selected, prevent selecting the same node
    if (endNode === event.target.value) {
      setEndNode('');
    }
  };
  
  // Function to handle end node selection
  const handleEndNodeChange = (event) => {
    setEndNode(event.target.value);
    // Clear the current path when changing nodes
    setCurrentPath(null);
    setDebugInfo(null);
    // If start node is already selected, prevent selecting the same node
    if (startNode === event.target.value) {
      setStartNode('');
    }
  };
  
  // Function to find the path
  const findPath = () => {
    if (!startNode || !endNode) {
      setError('Please select both start and end points');
      return;
    }
    
    try {
      setLoading(true);
      setError(null);
      setDebugInfo(null);
      
      // Using setTimeout to allow the UI to update before the calculation
      setTimeout(() => {
        // Get node connection information for debugging
        const startConnections = getNodeConnections(startNode);
        const endConnections = getNodeConnections(endNode);
        
        // Store debugging information
        const debugData = {
          startNode: startNode,
          startConnections: startConnections,
          endNode: endNode,
          endConnections: endConnections
        };
        
        // Find the shortest path using A* algorithm
        const result = findShortestPath(startNode, endNode);
        
        if (result.path.length === 0) {
          setError('No path found between these locations');
          setCurrentPath(null);
          setDebugInfo(debugData);
        } else {
          // Generate directions
          const directions = generateDirections(result.path);
          
          // Set the current path with all details
          setCurrentPath({
            ...result,
            directions
          });
          
          // Store debug info with successful path
          if (debug) {
            debugData.pathNodes = result.path.map(node => node.name);
            setDebugInfo(debugData);
          }
        }
        
        setLoading(false);
      }, 10);
    } catch (err) {
      setError('Failed to find a path between the selected locations. Please try again.');
      setLoading(false);
      console.error(err);
    }
  };
  
  // Function to handle the reset button
  const handleReset = () => {
    setStartNode('');
    setEndNode('');
    setCurrentPath(null);
    setDebugInfo(null);
  };
  
  // Add handler for map clicks to select rooms
  const handleMapClick = useCallback((e) => {
    if (!mapContainerRef.current) return;
    
    // Get click coordinates relative to the map
    const rect = mapContainerRef.current.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    
    // Scale to match the original coordinates
    const scaleX = imageDimensions.width / rect.width;
    const scaleY = imageDimensions.height / rect.height;
    
    const clickX = x * scaleX;
    const clickY = y * scaleY;
    
    // Find closest room to the click (within a threshold)
    const threshold = isMobile ? 40 : 30; // Larger threshold for mobile
    let closestRoom = null;
    let minDistance = threshold;
    
    rooms.forEach(room => {
      const distance = Math.sqrt(
        Math.pow(room.x - clickX, 2) + Math.pow(room.y - clickY, 2)
      );
      
      if (distance < minDistance) {
        minDistance = distance;
        closestRoom = room;
      }
    });
    
    if (closestRoom) {
      // If no start node is selected, set it as start
      if (!startNode) {
        setStartNode(closestRoom.name);
      } 
      // Otherwise, if the clicked room isn't the start node, set it as end
      else if (closestRoom.name !== startNode && !endNode) {
        setEndNode(closestRoom.name);
      }
      // If both are already set, update the end node
      else if (closestRoom.name !== startNode) {
        setEndNode(closestRoom.name);
        setCurrentPath(null);
        setDebugInfo(null);
      }
    }
  }, [startNode, endNode, rooms, imageDimensions, isMobile]);
  
  return (
    <Box className="navigation-container">
      <Typography 
        variant="h5" 
        component="h2" 
        gutterBottom 
        sx={{ 
          fontSize: isMobile ? '1.3rem' : '1.6rem',
          fontWeight: 500,
          display: 'flex',
          alignItems: 'center',
          bgcolor: 'rgba(25, 118, 210, 0.08)',
          p: 1.5,
          borderRadius: 2,
          mb: 2,
          boxShadow: '0 1px 3px rgba(0,0,0,0.05)'
        }}
      >
        <ExploreIcon sx={{ mr: 1.5, color: '#1976d2' }} /> 
        Campus Navigation
      </Typography>
      
      <Typography 
        variant="body2" 
        color="text.secondary" 
        sx={{ 
          mb: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'flex-start',
          p: 1.5,
          bgcolor: 'rgba(25, 118, 210, 0.08)',
          borderRadius: 1.5,
          border: '1px solid rgba(25, 118, 210, 0.12)'
        }}
      >
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 0.5 }}>
          <InfoIcon fontSize="small" sx={{ mr: 0.8, color: '#1976d2' }} />
          Use the interactive map below to find the shortest path between two locations on campus.
        </Box>
        <Box sx={{ pl: 3.5 }}>
          Directions are given assuming north is at the top of the map.
        </Box>
      </Typography>

      
      
      {initialLoading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', my: 8 }}>
          <CircularProgress size={isMobile ? 40 : 50} thickness={4} />
          <Typography variant="body1" sx={{ mt: 2, fontWeight: 500 }}>
            Loading navigation data...
          </Typography>
        </Box>
      ) : (
        <>
          <Grid container spacing={isMobile ? 1 : 2} sx={{ mb: isMobile ? 1 : 2 }}>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth size={isMobile ? "small" : "medium"}>
                <InputLabel id="start-node-label">
                  <MyLocationIcon fontSize="small" sx={{ mr: 1 }} /> 
                  Starting Point
                </InputLabel>
                <Select
                  labelId="start-node-label"
                  id="start-node"
                  value={startNode}
                  onChange={handleStartNodeChange}
                  label="Starting Point"
                >
                  <MenuItem value="">
                    <em>Select start location</em>
                  </MenuItem>
                  {rooms.map((room) => (
                    <MenuItem key={`start-${room.name}`} value={room.name}>
                      {room.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
            
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth size={isMobile ? "small" : "medium"}>
                <InputLabel id="end-node-label">
                  <LocationOnIcon fontSize="small" sx={{ mr: 1 }} /> 
                  Destination
                </InputLabel>
                <Select
                  labelId="end-node-label"
                  id="end-node"
                  value={endNode}
                  onChange={handleEndNodeChange}
                  label="Destination"
                >
                  <MenuItem value="">
                    <em>Select destination</em>
                  </MenuItem>
                  {rooms.map((room) => (
                    <MenuItem key={`end-${room.name}`} value={room.name}>
                      {room.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </Grid>
          
          <Box 
            sx={{ 
              display: 'flex', 
              flexDirection: isMobile ? 'column' : 'row',
              justifyContent: 'space-between', 
              alignItems: isMobile ? 'flex-start' : 'center', 
              mb: isMobile ? 2 : 3,
              gap: isMobile ? 1.5 : 0,
              bgcolor: '#f9f9f9',
              p: 1.5,
              borderRadius: 2
            }}
          >
            <Box sx={{ 
              display: 'flex', 
              gap: 1.5,
              width: isMobile ? '100%' : 'auto',
              mb: isMobile ? 1 : 0
            }}>
              <Button
                variant="contained"
                color="primary"
                disabled={!startNode || !endNode || loading}
                onClick={findPath}
                startIcon={<RouteIcon />}
                fullWidth={isMobile}
                size={isMobile ? "medium" : "medium"}
                sx={{ 
                  px: 3,
                  py: 1,
                  borderRadius: 1.5,
                  fontWeight: 500,
                  boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                  textTransform: 'none',
                  '&:hover': {
                    boxShadow: '0 4px 8px rgba(0,0,0,0.15)'
                  }
                }}
              >
                {isMobile ? "Find Route" : "Find Directions"}
              </Button>
              
              {isMobile ? (
                <IconButton 
                  onClick={handleReset} 
                  color="primary" 
                  disabled={!startNode && !endNode}
                  sx={{ border: '1px solid #e0e0e0', borderRadius: 1.5 }}
                >
                  <RestartAltIcon />
                </IconButton>
              ) : (
                <Button
                  variant="contained"
                  onClick={handleReset}
                  size="medium"
                  startIcon={<RestartAltIcon />}
                  disabled={!startNode && !endNode}
                  sx={{ 
                    borderRadius: 1.5,
                    textTransform: 'none',
                    px: 2
                  }}
                >
                  Reset
                </Button>
              )}
            </Box>
            
            <Tooltip title={debug ? "Disable debug information" : "Show advanced path-finding information"}>
              <FormControlLabel
                control={
                  <Switch
                    checked={debug}
                    onChange={(e) => setDebug(e.target.checked)}
                    name="debug"
                    color="primary"
                    size={isMobile ? "small" : "medium"}
                  />
                }
                label={isMobile ? "Debug Mode" : "Show Debug Info"}
                sx={{ 
                  m: 0,
                  px: 1,
                  py: 0.5,
                  borderRadius: 1,
                  bgcolor: debug ? 'rgba(25, 118, 210, 0.08)' : 'transparent',
                  border: debug ? '1px solid rgba(25, 118, 210, 0.2)' : 'none',
                  '.MuiFormControlLabel-label': {
                    fontSize: isMobile ? '0.8rem' : 'inherit',
                    color: debug ? '#1976d2' : 'inherit',
                    fontWeight: debug ? 500 : 'inherit'
                  }
                }}
              />
            </Tooltip>
          </Box>
          
          {loading && (
            <Box sx={{ 
              display: 'flex', 
              alignItems: 'center', 
              justifyContent: 'center',
              my: 2,
              p: 2,
              borderRadius: 2,
              bgcolor: '#e3f2fd',
              border: '1px solid #bbdefb'
            }}>
              <CircularProgress size={24} sx={{ mr: 2, color: '#1976d2' }} />
              <Typography 
                variant={isMobile ? "body2" : "body1"}
                sx={{ fontWeight: 500, color: '#1976d2' }}
              >
                Finding the optimal route between locations...
              </Typography>
            </Box>
          )}
          
          {isMobile && (
            <Box sx={{ 
              display: 'flex', 
              alignItems: 'center', 
              mb: 2,
              p: 1,
              borderRadius: 1,
              bgcolor: 'rgba(0, 0, 0, 0.02)'
            }}>
              <InfoIcon fontSize="small" sx={{ mr: 0.5, color: 'text.secondary' }} />
              <Typography variant="caption" color="text.secondary">
                Tap rooms on the map to select them
              </Typography>
            </Box>
          )}

          {error && (
            <Box sx={{ 
              my: 2, 
              p: 1.5, 
              bgcolor: '#ffebee', 
              borderRadius: 2, 
              display: 'flex', 
              alignItems: 'center',
              boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
              borderLeft: '4px solid #f44336'
            }}>
              <InfoIcon color="error" sx={{ mr: 1.5 }} />
              <Typography color="error" variant={isMobile ? "body2" : "body1"}>
                {error}
              </Typography>
            </Box>
          )}
           
          {/* Reference map for selecting rooms - no path drawing */}
          <Box 
            ref={mapContainerRef} 
            className="map-container" 
            onClick={handleMapClick}
            style={{ cursor: 'pointer', position: 'relative' }}
          >
            <img 
              src={floorPlanImg} 
              alt="Floor Plan" 
              className="floor-plan" 
              onLoad={handleImageLoad}
            />
            <CompassRose />
            
            {/* Show markers for selected rooms
            {startNode && rooms.length > 0 && imageDimensions.width > 0 && (
              <RoomMarker 
                room={rooms.find(r => r.name === startNode)} 
                type="start" 
                dimension={imageDimensions}
                onClick={() => {
                  setStartNode('');
                  setCurrentPath(null);
                  setDebugInfo(null);
                }}
              />
            )}
            
            {endNode && rooms.length > 0 && imageDimensions.width > 0 && (
              <RoomMarker 
                room={rooms.find(r => r.name === endNode)} 
                type="end" 
                dimension={imageDimensions}
                onClick={() => {
                  setEndNode('');
                  setCurrentPath(null);
                  setDebugInfo(null);
                }}
              />
            )} */}
          </Box>
           
          {/* Debug information when debug mode is enabled */}
          {debug && debugInfo && (
            <Alert 
              severity="info" 
              icon={<BugReportIcon />}
              variant="filled"
              sx={{ 
                mt: 2, 
                mb: 2,
                borderRadius: 2,
                '& .MuiAlert-message': {
                  width: '100%'
                }
              }}
            >
              <Typography variant="subtitle2" sx={{ mb: 1, fontWeight: 600 }}>Path Debugging Information:</Typography>
              <Box sx={{ 
                p: 1.5, 
                bgcolor: 'rgba(255,255,255,0.15)', 
                borderRadius: 1,
                mb: 1
              }}>
                <Typography variant="body2">
                  <strong>Start:</strong> {debugInfo.startNode} (Connections: {debugInfo.startConnections?.length || 0})
                  {debugInfo.startConnections && 
                    <span> - Connected to: {debugInfo.startConnections.join(', ')}</span>
                  }
                </Typography>
                <Typography variant="body2">
                  <strong>End:</strong> {debugInfo.endNode} (Connections: {debugInfo.endConnections?.length || 0})
                  {debugInfo.endConnections && 
                    <span> - Connected to: {debugInfo.endConnections.join(', ')}</span>
                  }
                </Typography>
              </Box>
              {debugInfo.pathNodes && (
                <Typography variant="body2">
                  <strong>Path nodes:</strong> {debugInfo.pathNodes.join(' → ')}
                </Typography>
              )}
            </Alert>
          )}
           
          {/* Directions display with enhanced styling */}
          {currentPath && currentPath.path.length > 0 && (
            <Paper 
              sx={{ 
                mt: 2, 
                p: isMobile ? 2 : 3, 
                borderRadius: 2,
                boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
                border: '1px solid #e0e0e0'
              }} 
              elevation={2}
            >
              <Typography 
                variant={isMobile ? "h6" : "h5"} 
                gutterBottom 
                sx={{ 
                  display: 'flex', 
                  alignItems: 'center', 
                  color: '#1976d2',
                  fontWeight: 500
                }}
              >
                <RouteIcon sx={{ mr: 1 }} /> Route Directions
              </Typography>
              
              <Box sx={{ 
                mb: 2, 
                display: 'flex', 
                flexWrap: 'wrap', 
                gap: 1
              }}>
                <Chip 
                  icon={<RouteIcon />} 
                  label={`Distance: ${Math.round(currentPath.distance)} meters`} 
                  color="primary" 
                  variant="outlined"
                  size={isMobile ? "small" : "medium"}
                />
              </Box>
              
              <Divider sx={{ my: 2 }} />
              
              <Typography 
                variant={isMobile ? "subtitle2" : "subtitle1"} 
                gutterBottom 
                sx={{ 
                  fontWeight: 'bold',
                  display: 'flex',
                  alignItems: 'center',
                  mb: 1.5
                }}
              >
                <NavigationIcon sx={{ mr: 1, fontSize: isMobile ? '1rem' : '1.2rem' }} />
                Step-by-step directions:
              </Typography>
              <Box className="directions"
                sx={{
                  maxHeight: { xs: '250px', sm: '350px' }, 
                  overflowY: 'auto',
                  lineHeight: 1, 
                  p: 2, 
                  bgcolor: '#fafafa',
                  border: '1px solid #eee',
                  borderRadius: 1.5,
                  fontSize: '1rem',
                }}
              >
                {/* Check if currentPath.directions exists and is a string */}
                {currentPath && typeof currentPath.directions === 'string' &&
                  // Split the string by newlines and map each line to a styled paragraph
                  currentPath.directions.split('\n').filter(step => step.trim() !== '').map((step, index) => {
                    // Style special for different kinds of directions
                    let color = 'inherit';
                    let fontWeight = 400;
                    let icon = null;
                    
                    if (step.startsWith('Start')) {
                      color = '#4caf50'; // Green for start
                      fontWeight = 600;
                      icon = <MyLocationIcon fontSize="small" sx={{ mr: 1, color: '#4caf50' }} />;
                    } else if (step.startsWith('Turn')) {
                      color = '#ff9800'; // Orange for turns
                      fontWeight = 600;
                      icon = <NavigationIcon fontSize="small" sx={{ mr: 1, color: '#ff9800' }} />;
                    } else if (step.includes('should be on your')) {
                      color = '#f44336'; // Red for destination
                      fontWeight = 600;
                      icon = <LocationOnIcon fontSize="small" sx={{ mr: 1, color: '#f44336' }} />;
                    } else if (step.startsWith('Total distance')) {
                      color = '#1976d2'; // Blue for summary
                      fontWeight = 600;
                      icon = <RouteIcon fontSize="small" sx={{ mr: 1, color: '#1976d2' }} />;
                    } else if (step.startsWith('Walk')) {
                      icon = <ArrowUpwardIcon fontSize="small" sx={{ mr: 1, color: '#616161' }} />;
                    } else if (step.startsWith('Continue')) {
                      icon = <ArrowForwardIcon fontSize="small" sx={{ mr: 1, color: '#616161' }} />;
                    }
                    
                    return (
                      <Typography
                        key={index}
                        variant="body2"
                        component="p"
                        sx={{ 
                          mb: 1.5,
                          color,
                          fontWeight,
                          display: 'flex',
                          alignItems: 'center',
                          pl: 0,
                        }}
                      >
                        {icon}
                        {step.trim()}
                      </Typography>
                    );
                  })
                }
                {/* Handle cases where directions might not be a string or are empty */}
                {currentPath && typeof currentPath.directions !== 'string' && (
                  <Typography variant="body2" color="error">Error: Directions format incorrect.</Typography>
                )}
              </Box>
            </Paper>
          )}
        </>
      )}
    </Box>
  );
};

export default NavigationMap; 