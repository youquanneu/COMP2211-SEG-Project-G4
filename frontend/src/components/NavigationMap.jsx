// My campus navigation component 
import React, { useState, useRef, useEffect } from 'react';
import { rooms, waypoints, allNodes, isRoom, navigationConfig } from '../data/navigationData';
import { findPath, getNodeById, getNodePosition, verifyConnections } from '../utils/navigationUtils';
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
import MyLocationIcon from '@mui/icons-material/MyLocation';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import RouteIcon from '@mui/icons-material/Route';
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import ExploreIcon from '@mui/icons-material/Explore';
import InfoIcon from '@mui/icons-material/Info';
import NavigationIcon from '@mui/icons-material/Navigation';
import LabelIcon from '@mui/icons-material/Label';
import LabelOffIcon from '@mui/icons-material/LabelOff';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate } from 'react-router-dom';
import './NavigationMap.css'; // Styles for this component
// Using direct import - no more relative path issues
import floorPlanImage from '../assets/floor-plan.png';

const CampusNavigation = () => {
  // Theme and media query for responsive design
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const navigate = useNavigate(); // For navigation
  
  const [startRoom, setStartRoom] = useState("");
  const [endRoom, setEndRoom] = useState("");
  const [path, setPath] = useState([]);
  const canvasRef = useRef(null);
  const imgRef = useRef(null);
  const [imageLoaded, setImageLoaded] = useState(false);
  const [animationFrame, setAnimationFrame] = useState(0);
  const animationRef = useRef(null);
  const [showWaypoints, setShowWaypoints] = useState(true);
  const [showNodeLabels, setShowNodeLabels] = useState(navigationConfig.showNodeLabels);
  const [connectionStatus, setConnectionStatus] = useState(null);
  const [loading, setLoading] = useState(false);

  // Load the floor plan image
  useEffect(() => {
    const img = new Image();
    img.src = floorPlanImage; // Using imported image
    console.log("Loading image from:", floorPlanImage);
    
    img.onload = () => {
      console.log("Image loaded successfully!");
      imgRef.current = img;
      setImageLoaded(true);
    };
    
    img.onerror = (error) => {
      console.error("Error loading image:", error);
    };
  }, []);

  // Animation loop for path markers - makes them move along the path
  useEffect(() => {
    if (path.length <= 1) return;
    
    const animate = () => {
      setAnimationFrame(prev => (prev + 1) % 60); // 60 frames cycle
      animationRef.current = requestAnimationFrame(animate);
    };
    
    // Start animation
    animationRef.current = requestAnimationFrame(animate);
    
    // Cleanup - cancel animation when component unmounts
    return () => {
      if (animationRef.current) {
        cancelAnimationFrame(animationRef.current);
        animationRef.current = null;
      }
    };
  }, [path]);

  // Draw the rooms and path on canvas
  useEffect(() => {
    if (!canvasRef.current || !imageLoaded || !imgRef.current) return;

    const canvas = canvasRef.current;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // Clear canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw the floor plan image
    ctx.drawImage(imgRef.current, 0, 0, canvas.width, canvas.height);

    // Draw rooms with highlighting
    rooms.forEach(room => {
      // Use red highlight for disconnected rooms
      const isDisconnected = connectionStatus?.disconnectedRooms.includes(room.id);
      // Make boxes fully transparent (alpha = 0) to hide them
      ctx.fillStyle = 'rgba(0, 0, 0, 0)';
      ctx.fillRect(room.x, room.y, room.width, room.height);
      
      // Draw room labels if enabled in config
      if (showNodeLabels) {
        ctx.font = 'bold 12px Arial';
        ctx.fillStyle = 'rgba(0, 0, 0, 0.7)';
        ctx.fillText(room.id, room.x + 5, room.y + 15);
      }
    });

    // Draw waypoints if enabled
    if (showWaypoints) {
      waypoints.forEach(waypoint => {
        ctx.beginPath();
        
        if (waypoint.type === 'junction') {
          // Blue circle for junctions
          ctx.fillStyle = 'rgba(0, 0, 255, 0.7)';
          ctx.arc(waypoint.x, waypoint.y, 6, 0, Math.PI * 2);
        } else {
          // Red circle for corridors
          ctx.fillStyle = 'rgba(255, 0, 0, 0.7)';
          ctx.arc(waypoint.x, waypoint.y, 4, 0, Math.PI * 2);
        }
        
        ctx.fill();
        
        // Draw all waypoint IDs if enabled, not just junctions
        if (showNodeLabels) {
          ctx.font = waypoint.type === 'junction' ? 'bold 10px Arial' : '9px Arial';
          ctx.fillStyle = waypoint.type === 'junction' ? 'rgba(0, 0, 100, 0.8)' : 'rgba(150, 0, 0, 0.7)';
          
          // Position the label based on node type
          if (waypoint.type === 'junction') {
            ctx.fillText(waypoint.id, waypoint.x - 8, waypoint.y - 8);
          } else {
            ctx.fillText(waypoint.id, waypoint.x + 6, waypoint.y - 2);
          }
        } else if (waypoint.type === 'junction') {
          // Always show junction IDs as before
          ctx.font = '10px Arial';
          ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
          ctx.fillText(waypoint.id, waypoint.x - 8, waypoint.y - 8);
        }
      });
    }

    // Draw path if we have one
    if (path.length > 1) {
      const nodeMap = new Map();
      allNodes.forEach(node => nodeMap.set(node.id, node));

      // Draw the path
      ctx.beginPath();
      ctx.lineWidth = 3;
      ctx.strokeStyle = 'rgba(0, 128, 0, 0.8)';
      
      let isFirstPoint = true;
      for (const nodeId of path) {
        const node = nodeMap.get(nodeId);
        if (!node) continue;
        
        const pos = getNodePosition(node);
        
        if (isFirstPoint) {
          ctx.moveTo(pos.x, pos.y);
          isFirstPoint = false;
        } else {
          ctx.lineTo(pos.x, pos.y);
        }
      }
      ctx.stroke();

      // Draw animated arrows along the path 
      const arrowPositions = [];
      const totalArrows = Math.min(path.length - 1, 5); // Max 5 arrows
      
      for (let i = 0; i < totalArrows; i++) {
        const position = (i / totalArrows) + (animationFrame / 240); // Animation speed
        const segmentIndex = Math.floor(position * (path.length - 1)) % (path.length - 1);
        const segmentPosition = (position * (path.length - 1)) % 1;
        
        const node1 = nodeMap.get(path[segmentIndex]);
        const node2 = nodeMap.get(path[segmentIndex + 1]);
        
        if (!node1 || !node2) continue;
        
        const pos1 = getNodePosition(node1);
        const pos2 = getNodePosition(node2);
        
        const arrowX = pos1.x + segmentPosition * (pos2.x - pos1.x);
        const arrowY = pos1.y + segmentPosition * (pos2.y - pos1.y);
        
        // Calculate angle for the arrow
        const angle = Math.atan2(pos2.y - pos1.y, pos2.x - pos1.x);
        
        arrowPositions.push({ x: arrowX, y: arrowY, angle });
      }
      
      // Draw each arrow
      arrowPositions.forEach(({ x, y, angle }) => {
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(angle);
        
        // Draw arrow
        ctx.beginPath();
        ctx.fillStyle = 'rgba(0, 128, 0, 0.9)';
        ctx.moveTo(10, 0);  // Arrow tip
        ctx.lineTo(-5, 5);  // Bottom corner
        ctx.lineTo(-2, 0);  // Middle indent
        ctx.lineTo(-5, -5); // Top corner
        ctx.closePath();
        ctx.fill();
        
        ctx.restore();
      });

      // Highlight the start and end rooms
      const startNode = nodeMap.get(path[0]);
      const endNode = nodeMap.get(path[path.length - 1]);

      if (startNode && isRoom(startNode)) {
        // Start room - make transparent to hide
        ctx.fillStyle = 'rgba(0, 0, 0, 0)';
        ctx.fillRect(startNode.x, startNode.y, startNode.width, startNode.height);
      }

      if (endNode && isRoom(endNode)) {
        // End room - make transparent to hide
        ctx.fillStyle = 'rgba(0, 0, 0, 0)';
        ctx.fillRect(endNode.x, endNode.y, endNode.width, endNode.height);
      }

      // Draw path nodes (dots at junction points)
      path.forEach(nodeId => {
        const node = nodeMap.get(nodeId);
        if (!node || isRoom(node)) return;
        
        const pos = getNodePosition(node);
        ctx.beginPath();
        
        if (node.type === 'junction') {
          // Highlight junction points more prominently
          ctx.fillStyle = 'rgba(0, 128, 0, 0.9)';
          ctx.arc(pos.x, pos.y, 6, 0, Math.PI * 2);
        } else {
          // Highlight corridor points
          ctx.fillStyle = 'rgba(0, 128, 0, 0.7)';
          ctx.arc(pos.x, pos.y, 4, 0, Math.PI * 2);
        }
        
        ctx.fill();
        
        // Highlight the node IDs along the path
        if (showNodeLabels) {
          ctx.font = 'bold 11px Arial';
          ctx.fillStyle = 'rgba(0, 100, 0, 0.9)';
          ctx.fillText(node.id, pos.x + 7, pos.y - 3);
        }
      });
    }
  }, [path, imageLoaded, animationFrame, showWaypoints, showNodeLabels, connectionStatus]);

  // Toggle node labels
  const toggleNodeLabels = () => {
    setShowNodeLabels(!showNodeLabels);
  };

  // Check connectivity between all rooms
  const checkConnectivity = () => {
    setLoading(true);
    
    // Using setTimeout to let UI update first
    setTimeout(() => {
      const { connectedRooms, disconnectedRooms } = verifyConnections();
      
      setConnectionStatus({
        verified: true,
        connectedRooms,
        disconnectedRooms
      });
      
      setLoading(false);
    }, 10);
  };

  // Calculate path between rooms
  const calculatePath = () => {
    if (!startRoom || !endRoom) {
      return;
    }
    
    setLoading(true);
    
    // Using setTimeout so we don't block
    setTimeout(() => {
      const foundPath = findPath(startRoom, endRoom);
      setPath(foundPath);
      setLoading(false);
    }, 10);
  };

  // Reset the path
  const resetPath = () => {
    setStartRoom("");
    setEndRoom("");
    setPath([]);
    setConnectionStatus(null);
  };

  // Get only room IDs for dropdown menus
  const roomOptions = rooms.map(room => ({
    id: room.id,
    name: room.name
  }));

  // Generate navigation instructions
  const generateInstructions = () => {
    if (!path || path.length < 2) return [];
    
    const instructions = [];
    
    path.forEach((nodeId, index) => {
      const node = getNodeById(nodeId);
      if (!node) return;
      
      if (isRoom(node)) {
        if (index === 0) {
          instructions.push({
            type: 'start',
            text: `Start at ${nodeId} (${node.name})`
          });
        } else if (index === path.length - 1) {
          instructions.push({
            type: 'destination',
            text: `Arrive at destination ${nodeId} (${node.name})`
          });
        } else {
          instructions.push({
            type: 'room',
            text: `Enter room ${nodeId} (${node.name})`
          });
        }
      } else if (node.type === 'junction') {
        instructions.push({
          type: 'junction',
          text: `Turn at corridor junction ${nodeId}`
        });
      } else {
        const prevNode = index > 0 ? getNodeById(path[index - 1]) : null;
        const nextNode = index < path.length - 1 ? getNodeById(path[index + 1]) : null;
        
        if (prevNode && nextNode && isRoom(prevNode) && !isRoom(nextNode)) {
          instructions.push({
            type: 'corridor',
            text: 'Exit room and proceed through corridor'
          });
        } else if (prevNode && nextNode && !isRoom(prevNode) && isRoom(nextNode)) {
          instructions.push({
            type: 'approach',
            text: `Approach the entrance of ${nextNode.id}`
          });
        } else {
          instructions.push({
            type: 'continue',
            text: 'Continue through corridor'
          });
        }
      }
    });
    
    return instructions;
  };

  const instructions = generateInstructions();

  return (
    <Box className="navigation-container">
      <Box sx={{ display: 'flex', justifyContent: 'flex-start', mb: 1 }}>
        <IconButton
          color="primary"
          onClick={() => navigate('/userhome')}
          size="small"
          sx={{
            borderRadius: 1,
            border: '1px solid rgba(25, 118, 210, 0.5)',
            p: 0.8,
            bgcolor: 'rgba(25, 118, 210, 0.08)',
            '&:hover': {
              bgcolor: 'rgba(25, 118, 210, 0.15)',
            }
          }}
          aria-label="go to home"
        >
          <ArrowBackIcon fontSize="small" />
        </IconButton>
      </Box>
      
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
      </Typography>
      
      {!imageLoaded ? (
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
                  value={startRoom}
                  onChange={(e) => setStartRoom(e.target.value)}
                  label="Starting Point"
                >
                  <MenuItem value="">
                    <em>Select start location</em>
                  </MenuItem>
                  {roomOptions.map((room) => (
                    <MenuItem key={`start-${room.id}`} value={room.id}>
                      {room.id} - {room.name}
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
                  value={endRoom}
                  onChange={(e) => setEndRoom(e.target.value)}
                  label="Destination"
                >
                  <MenuItem value="">
                    <em>Select destination</em>
                  </MenuItem>
                  {roomOptions.map((room) => (
                    <MenuItem key={`end-${room.id}`} value={room.id}>
                      {room.id} - {room.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </Grid>
          
          <Box 
            sx={{ 
              display: 'flex', 
              flexDirection: 'row',
              justifyContent: 'center', 
              alignItems: 'center', 
              mb: isMobile ? 2 : 3,
              bgcolor: '#f9f9f9',
              p: 1.5,
              borderRadius: 2,
              width: '100%',
              overflow: 'hidden'
            }}
          >
            <Box sx={{ 
              display: 'flex', 
              flexWrap: 'wrap',
              justifyContent: 'center',
              gap: 1,
              width: '100%',
            }}>
              <Button
                variant="contained"
                color="primary"
                disabled={!startRoom || !endRoom || loading}
                onClick={calculatePath}
                startIcon={<RouteIcon />}
                size="small"
                sx={{ 
                  px: 1.5,
                  py: 0.7,
                  borderRadius: 1.5,
                  fontWeight: 500,
                  boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                  textTransform: 'none',
                  minWidth: 0,
                  '&:hover': {
                    boxShadow: '0 4px 8px rgba(0,0,0,0.15)'
                  }
                }}
              >
                {isMobile ? "Find" : "Find Directions"}
              </Button>
              
              <Button
                variant="outlined"
                onClick={resetPath}
                size="small"
                startIcon={<RestartAltIcon />}
                disabled={!startRoom && !endRoom}
                sx={{ 
                  borderRadius: 1.5,
                  textTransform: 'none',
                  px: 1.5,
                  py: 0.7,
                  minWidth: 0
                }}
              >
                Reset
              </Button>
              
              <Tooltip title={showWaypoints ? "Hide waypoints" : "Show waypoints"}>
                <Button
                  variant={showWaypoints ? "contained" : "outlined"}
                  color="info"
                  onClick={() => setShowWaypoints(!showWaypoints)}
                  startIcon={showWaypoints ? <VisibilityOffIcon /> : <VisibilityIcon />}
                  size="small"
                  sx={{ 
                    borderRadius: 1.5,
                    textTransform: 'none',
                    px: 1.5,
                    py: 0.7,
                    minWidth: 0
                  }}
                >
                  {isMobile ? "" : "Waypoints"}
                </Button>
              </Tooltip>
              
              <Tooltip title={showNodeLabels ? "Hide node labels" : "Show node labels"}>
                <Button
                  variant={showNodeLabels ? "contained" : "outlined"}
                  color="secondary"
                  onClick={toggleNodeLabels}
                  startIcon={showNodeLabels ? <LabelOffIcon /> : <LabelIcon />}
                  size="small"
                  sx={{ 
                    borderRadius: 1.5,
                    textTransform: 'none',
                    px: 1.5,
                    py: 0.7,
                    minWidth: 0
                  }}
                >
                  {isMobile ? "" : "Labels"}
                </Button>
              </Tooltip>
              
              <Tooltip title="Check if all rooms are properly connected">
                <Button
                  variant="outlined"
                  color="secondary"
                  onClick={checkConnectivity}
                  startIcon={<CheckCircleOutlineIcon />}
                  size="small"
                  sx={{ 
                    borderRadius: 1.5,
                    textTransform: 'none',
                    px: 1.5,
                    py: 0.7,
                    minWidth: 0
                  }}
                >
                  {isMobile ? "" : "Verify"}
                </Button>
              </Tooltip>
            </Box>
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
                Processing navigation data...
              </Typography>
            </Box>
          )}

          {connectionStatus?.verified && (
            <Alert 
              severity={connectionStatus.disconnectedRooms.length === 0 ? "success" : "error"}
              sx={{ 
                mt: 2, 
                mb: 2,
                borderRadius: 2,
              }}
            >
              <Typography variant="subtitle2" sx={{ mb: 1, fontWeight: 600 }}>
                {connectionStatus.disconnectedRooms.length === 0
                  ? 'All rooms are connected!'
                  : `${connectionStatus.disconnectedRooms.length} rooms are not fully connected`}
              </Typography>
              
              {connectionStatus.disconnectedRooms.length > 0 && (
                <>
                  <Typography variant="body2">
                    The following rooms may not be reachable from all other rooms:
                  </Typography>
                  <Box sx={{ 
                    display: 'flex', 
                    flexWrap: 'wrap', 
                    gap: 0.5,
                    mt: 1
                  }}>
                    {connectionStatus.disconnectedRooms.map(roomId => (
                      <Chip 
                        key={roomId} 
                        label={roomId} 
                        size="small" 
                        color="error" 
                        variant="outlined"
                      />
                    ))}
                  </Box>
                </>
              )}
            </Alert>
          )}
           
          {/* Map container with canvas - tried to make this responsive but it gets weird */}
          <Box 
            sx={{ 
              position: 'relative',
              width: '100%',
              border: '1px solid #e0e0e0',
              borderRadius: 2,
              overflow: 'hidden',
              mb: 3
            }}
          >
            <canvas 
              ref={canvasRef}
              width={1200}
              height={1200}
              style={{ 
                width: '100%', 
                height: 'auto', 
                display: 'block',
                maxWidth: '100%'
              }}
            />
          </Box>
           
          {/* Path details and directions */}
          {path.length > 0 && (
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
                <RouteIcon sx={{ mr: 1 }} /> Route Details
              </Typography>
              
              <Box sx={{ 
                mb: 2, 
                display: 'flex', 
                flexWrap: 'wrap', 
                gap: 1
              }}>
                <Chip 
                  icon={<MyLocationIcon />} 
                  label={`From: ${startRoom} - ${rooms.find(r => r.id === startRoom)?.name}`} 
                  color="success" 
                  variant="outlined"
                  size={isMobile ? "small" : "medium"}
                />
                <Chip 
                  icon={<LocationOnIcon />} 
                  label={`To: ${endRoom} - ${rooms.find(r => r.id === endRoom)?.name}`} 
                  color="error" 
                  variant="outlined"
                  size={isMobile ? "small" : "medium"}
                />
                <Chip 
                  icon={<RouteIcon />} 
                  label={`Waypoints: ${path.length}`} 
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
                Navigation Instructions:
              </Typography>
              
              <Box
                sx={{
                  maxHeight: { xs: '250px', sm: '350px' }, 
                  overflowY: 'auto',
                  lineHeight: 1, 
                  p: 2, 
                  bgcolor: '#fafafa',
                  border: '1px solid #eee',
                  borderRadius: 1.5,
                }}
              >
                {instructions.map((instruction, index) => {
                  // Style based on instruction type - had to handle each case
                  let color = 'inherit';
                  let fontWeight = 400;
                  let icon = null;
                  
                  if (instruction.type === 'start') {
                    color = '#4caf50'; // Green for start
                    fontWeight = 600;
                    icon = <MyLocationIcon fontSize="small" sx={{ mr: 1, color: '#4caf50' }} />;
                  } else if (instruction.type === 'destination') {
                    color = '#f44336'; // Red for destination
                    fontWeight = 600;
                    icon = <LocationOnIcon fontSize="small" sx={{ mr: 1, color: '#f44336' }} />;
                  } else if (instruction.type === 'junction') {
                    color = '#ff9800'; // Orange for turns
                    fontWeight = 600;
                    icon = <NavigationIcon fontSize="small" sx={{ mr: 1, color: '#ff9800' }} />;
                  }
                  
                  return (
                    <Typography
                      key={`instruction-${index}`}
                      variant="body2"
                      component="p"
                      sx={{ 
                        mb: 1.5,
                        color,
                        fontWeight,
                        display: 'flex',
                        alignItems: 'center',
                      }}
                    >
                      {icon}
                      {index + 1}. {instruction.text}
                    </Typography>
                  );
                })}
              </Box>
            </Paper>
          )}
        </>
      )}
    </Box>
  );
};

export default CampusNavigation;